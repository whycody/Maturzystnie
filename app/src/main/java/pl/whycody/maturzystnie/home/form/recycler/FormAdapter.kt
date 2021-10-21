package pl.whycody.maturzystnie.home.form.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import pl.whycody.maturzystnie.R
import pl.whycody.maturzystnie.data.FormOption
import pl.whycody.maturzystnie.BR

class FormAdapter: ListAdapter<FormOption, FormAdapter.FormHolder>(FormDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FormHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(inflater,
            R.layout.item_form, parent, false)
        return FormHolder(binding)
    }

    override fun onBindViewHolder(holder: FormHolder, position: Int) = holder.setupData(getItem(position))

    inner class FormHolder(private val binding: ViewDataBinding): RecyclerView.ViewHolder(binding.root) {

        fun setupData(formOption: FormOption) {
            binding.setVariable(BR.formOption, formOption)
        }
    }
}