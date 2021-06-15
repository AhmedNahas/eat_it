package com.example.myapplication.ui.foodDetail

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myapplication.MyApplication
import com.example.myapplication.R
import com.example.myapplication.databinding.*
import com.example.myapplication.model.*
import com.example.myapplication.utils.Common
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.gson.Gson
import kotlin.math.roundToInt

class FoodDetailFragment : Fragment() {

    private var selectedAddons: MutableList<AddonModel>? = mutableListOf()
    private var selectedMealSize: SizeModel? = null
    private var currentUser: FirebaseUser? = null
    private var _binding: FragmentFoodDetailBinding? = null
    private val binding get() = _binding!!
    private val args: FoodDetailFragmentArgs by navArgs()
    private val viewModel: FoodDetailViewModel by viewModels()
    private lateinit var food: FoodModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_food_detail, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        subscribeToLiveData()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupUI() {
        food = args.food.copy()
        binding.topAppBar.setNavigationOnClickListener { findNavController().navigateUp() }
        var quantity = 1
        binding.btnIncrease.setOnClickListener {
            quantity++
            binding.tvFoodQuantity.text = quantity.toString()
            calculateTotalPrice()
        }
        binding.btnDecrease.setOnClickListener {
            if (quantity != 1) {
                quantity--
                binding.tvFoodQuantity.text = quantity.toString()
                calculateTotalPrice()
            }
        }

        binding.btnShowComment.setOnClickListener {
            findNavController().navigate(
                FoodDetailFragmentDirections.actionFoodDetailFragmentToCommentFragment(food.id!!)
            )
        }

        binding.rBFood.setOnTouchListener(View.OnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                showRatingBottomSheet()
            }
            return@OnTouchListener true
        })

        binding.tvAddon.setOnClickListener { showAddonBottomSheet() }

        setupMealSizeRadioButtons()
    }

    private fun subscribeToLiveData() {
        currentUser = FirebaseAuth.getInstance().currentUser
        binding.lifecycleOwner = this
        binding.food = food
        binding.rBFood.rating = food.ratingValue.toFloat()

        viewModel.getCommentLiveData().observe(viewLifecycleOwner, {
            FirebaseDatabase.getInstance().getReference(Common.COMMENT_REF)
                .child(food.id.toString()).push()
                .setValue(it).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "OnCompleteListener: ${task.isSuccessful}")
                        addRatingToFood(it.ratingValue)
                    }
                }
        })
    }

    private fun setupMealSizeRadioButtons() {
        for (mealSize in food.size!!) {
            val radioButton = RadioButton(context)
            radioButton.setOnCheckedChangeListener { _, b ->
                if (b) {
                    selectedMealSize = mealSize
                    calculateTotalPrice()
                }
            }
            val params = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1F)
            radioButton.layoutParams = params
            radioButton.text = mealSize.name
            radioButton.tag = mealSize.price
            binding.radioGroup.addView(radioButton)
        }
        if (binding.radioGroup.childCount > 0) {
            val nRadioBtn = binding.radioGroup.getChildAt(0) as RadioButton
            nRadioBtn.isChecked = true
        }
    }

    private fun calculateTotalPrice() {
        var totalPrice = food.price.toDouble()
        var displayPrice: Double

        //addons
        if (!selectedAddons.isNullOrEmpty())
            for (selectedAddon in selectedAddons!!) {
                totalPrice += selectedAddon.price
                Log.d(TAG, "calculateTotalPrice: addon : ${selectedAddon.price}")
            }
        //size
        totalPrice += selectedMealSize!!.price.toDouble()
        Log.d(TAG, "calculateTotalPrice: size : ${selectedMealSize!!.price}")
        val quantity = binding.tvFoodQuantity.text.toString().toInt()
        displayPrice = totalPrice * quantity
        displayPrice = (displayPrice * 100.0).roundToInt() / 100.0
        binding.tvFoodPrice.text =
            StringBuilder("").append(Common.formatPrice(displayPrice)).toString()
    }

    private fun showAddonBottomSheet() {
        val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)
        val dialogBinding = BottomsheetAddonBinding.inflate(LayoutInflater.from(requireContext()))
        displayAddonList(dialogBinding)
        bottomSheetDialog.setOnDismissListener {
            displayUserSelectedAddons(selectedAddons!!)
            calculateTotalPrice()
        }
        bottomSheetDialog.setContentView(dialogBinding.root)
        bottomSheetDialog.show()

    }

    private fun displayAddonList(dialogBinding: BottomsheetAddonBinding) {
        if (args.food.addon!!.isNotEmpty()) {
            binding.chipGroup.clearCheck()
            binding.chipGroup.removeAllViews()
            for (addonModel in args.food.addon!!) {
                val chip = ItemNormalChipBinding.inflate(layoutInflater)
                chip.chip.text = java.lang.StringBuilder(addonModel.name!!).append("(+$")
                    .append(addonModel.price).append(")")
                chip.chip.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        selectedAddons!!.add(addonModel)
                    }
                }
                dialogBinding.chipGroup.addView(chip.chip)
            }
        }
    }

    private fun displayUserSelectedAddons(displayAddonList: MutableList<AddonModel>) {
        if (displayAddonList.size > 0) {
            binding.chipGroup.removeAllViews()
            for (addonModel in displayAddonList) {
                val chip = ItemDeleteChipBinding.inflate(layoutInflater)
                chip.chip.text = java.lang.StringBuilder(addonModel.name!!).append("(+$")
                    .append(addonModel.price).append(")")
                chip.chip.isClickable = false
                chip.chip.setOnCloseIconClickListener {
                    binding.chipGroup.removeView(it)
                    displayAddonList.remove(addonModel)
                    calculateTotalPrice()
                }
                binding.chipGroup.addView(chip.chip)
            }
        } else if (displayAddonList.size == 0) {
            binding.chipGroup.removeAllViews()
        }
    }

    private fun showRatingBottomSheet() {
        val currUser = Gson().fromJson(
            MyApplication.getInstance()!!.prefrences.getCurrentUserInfo(),
            UserModel::class.java
        )
        val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)
        val dialogBinding = BottomsheetRatingBinding.inflate(LayoutInflater.from(requireContext()))
        dialogBinding.btnOkay.setOnClickListener {
            val comment = CommentModel()
            comment.comment = dialogBinding.etComment.text.toString()
            comment.name = currUser.name
            comment.uid = currentUser?.uid
            comment.ratingValue = dialogBinding.rBFood.rating
            val serverTimeStamp = HashMap<String, Any>()
            serverTimeStamp["timeStamp"] = ServerValue.TIMESTAMP
            comment.commentTimeStamp = serverTimeStamp
            viewModel.setComment(comment)
            binding.loading.visibility = View.VISIBLE
            bottomSheetDialog.hide()
        }
        dialogBinding.btnCancel.setOnClickListener { bottomSheetDialog.hide() }
        bottomSheetDialog.setContentView(dialogBinding.root)
        bottomSheetDialog.show()
    }

    private fun addRatingToFood(ratingValue: Float) {
        FirebaseDatabase.getInstance().getReference(Common.CATEGORY)
            .child(food.menuId.toString())
            .child(Common.FOODS_REF).child(food.key.toString())
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val food = snapshot.getValue(FoodModel::class.java)
                        food!!.key = food.key
                        //Apply rating
                        val sumRating = food.ratingValue + ratingValue
                        val ratingCount = food.ratingCount + 1
                        val result = sumRating / ratingCount

                        val updateData = HashMap<String, Any>()
                        updateData["ratingValue"] = result
                        updateData["ratingCount"] = ratingCount

                        food.ratingValue = result
                        food.ratingCount = ratingCount

                        snapshot.ref.updateChildren(updateData).addOnCompleteListener {
                            binding.loading.visibility = View.GONE
                            if (it.isSuccessful) {
                                Log.d(TAG, "onDataChange: ${it.isSuccessful}")
                                viewModel.setFood(food)
                                binding.rBFood.rating = result.toFloat()
                                Toast.makeText(requireContext(), "Thank you !", LENGTH_LONG).show()
                            }
                        }
                    } else
                        binding.loading.visibility = View.GONE
                }

                override fun onCancelled(error: DatabaseError) {
                    binding.loading.visibility = View.GONE
                    Toast.makeText(requireContext(), error.message, LENGTH_LONG).show()
                }

            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "FoodDetailFragment"
    }
}
