package pl.whycody.maturzystnie.home.form.recycler

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import pl.whycody.maturzystnie.R

class FormItemDecoration(context: Context): RecyclerView.ItemDecoration() {

    private val appPadding = context.resources.getDimension(R.dimen.app_padding)
    private val smallPadding = context.resources.getDimension(R.dimen.small_padding)

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView,
                                state: RecyclerView.State) {
        val params = view.layoutParams as RecyclerView.LayoutParams
        if(parent.getChildAdapterPosition(view) == 0)
            params.marginStart = appPadding.toInt()
        else params.marginStart = 0
        if(parent.getChildAdapterPosition(view) == parent.childCount-1)
            params.marginEnd = appPadding.toInt()
        else params.marginEnd = smallPadding.toInt()
        super.getItemOffsets(outRect, view, parent, state)
    }
}