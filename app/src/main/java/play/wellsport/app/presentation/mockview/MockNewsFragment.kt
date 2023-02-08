package play.wellsport.app.presentation.mockview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import play.wellsport.app.R
import play.wellsport.app.data.MockRepository
import play.wellsport.app.databinding.FragmentMockNewsBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MockNewsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MockNewsFragment : Fragment() {private var _binding: FragmentMockNewsBinding? = null
    private val binding get() = _binding!!

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            //param1 = it.getString(ARG_PARAM1)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMockNewsBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listNews = context?.let { MockRepository.getSportsNews(it) }
        val insertList = listNews?.map {
            ItemNews(it)
        }?.toList()
        binding.mockNewsRecyclerView.adapter = adapter.apply { insertList?.let { addAll(it) } }
    }

    companion object {

        fun newInstance(param1: String, param2: String) =
            MockNewsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}