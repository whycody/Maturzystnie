package pl.whycody.maturzystnie.home.form.recycler

import androidx.recyclerview.widget.DiffUtil
import pl.whycody.maturzystnie.data.FormOption
import pl.whycody.maturzystnie.data.FormQuestion

class FormDiffCallback: DiffUtil.ItemCallback<FormOption>() {

    override fun areItemsTheSame(oldItem: FormOption, newItem: FormOption)
        = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: FormOption, newItem: FormOption)
        = false
}