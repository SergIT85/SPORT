package play.wellsport.app.presentation.mockview

import android.view.View
import com.squareup.picasso.Picasso

import com.xwray.groupie.viewbinding.BindableItem
import play.wellsport.app.R
import play.wellsport.app.data.SportsNews
import play.wellsport.app.databinding.ItemMockFragmentBinding


class ItemNews(
    private val content: SportsNews
) : BindableItem<ItemMockFragmentBinding>()  {
    override fun bind(viewBinding: ItemMockFragmentBinding, position: Int) {
        viewBinding.textView.text = content.title
        Picasso.get()
            .load(content.imageId)
            .fit()
            .centerCrop()
            .into(viewBinding.imageSportsNews)
    }

    override fun getLayout(): Int = R.layout.item_mock_fragment

    override fun initializeViewBinding(view: View) = ItemMockFragmentBinding.bind(view)
}