package com.example.moneyme.presentation.money

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneyme.di.UseCaseModule
import com.example.moneyme.domain.model.Category
import com.example.moneyme.domain.model.Transaction
import com.example.moneyme.domain.model.TransactionType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDateTime
import javax.inject.Inject

data class MoneyInOutState(
    val amount: String = "",
    val note: String = "",
    val selectedCategory: Category? = null, // MoneyIn can be null
    val categories: List<Category> = emptyList(),
    val isSaving: Boolean = false,
    val errorMessage: String? = null,
    val dateTime: LocalDateTime = LocalDateTime.now()
)

@HiltViewModel
class MoneyInOutViewModel @Inject constructor(
    private val categoryUseCases: UseCaseModule.CategoryUseCases,
    private val transactionUseCases: UseCaseModule.TransactionUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(MoneyInOutState())
    val state: StateFlow<MoneyInOutState> = _state

    fun loadCategories() {
        viewModelScope.launch {
            categoryUseCases.getAllCategories().collect { cats ->
                _state.update { it.copy(categories = cats) }
            }
        }
    }

    fun initializeFromArgs(amount: Double, body: String) {
        _state.update { it.copy(amount = amount.toString(), note = body) }
    }

    fun onAmountChange(value: String) {
        _state.update { it.copy(amount = value) }
    }

    fun onNoteChange(value: String) {
        _state.update { it.copy(note = value) }
    }

    fun onCategorySelected(category: Category?) {
        _state.update { it.copy(selectedCategory = category) }
    }

    fun onDateTimeChange(value: LocalDateTime) {
        _state.update { it.copy(dateTime = value) }
    }

    fun saveTransaction(type: TransactionType) {
        viewModelScope.launch {
            try {
                _state.update { it.copy(isSaving = true, errorMessage = null) }

                val amountDouble = _state.value.amount.toDoubleOrNull()
                    ?: throw IllegalArgumentException("Invalid amount")

                val transaction = Transaction(
                    transactionId = 0,
                    dateAndTime = _state.value.dateTime,
                    amount = amountDouble,
                    type = type,
                    categoryId = if (type == TransactionType.OUT) _state.value.selectedCategory?.categoryId else null,
                    note = _state.value.note
                )

                transactionUseCases.addTransaction(transaction)
                _state.update { it.copy(isSaving = false) }

            } catch (e: Exception) {
                _state.update { it.copy(isSaving = false, errorMessage = e.message) }
            }
        }
    }
}