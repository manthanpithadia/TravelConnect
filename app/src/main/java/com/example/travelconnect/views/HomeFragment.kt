import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelconnect.R
import com.example.travelconnect.data.model.CardItem
import com.example.travelconnect.data.model.CardItemTypeThree
import com.example.travelconnect.data.model.CardItemTypeTwo
import com.example.travelconnect.databinding.FragmentHomeBinding
import com.example.travelconnect.viewmodels.HomeViewModel
import com.example.travelconnect.views.CardTypeOneAdapter
import com.example.travelconnect.views.CardTypeThreeAdapter
import com.example.travelconnect.views.CardTypeTwoAdapter

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        val fullText = "Travel Connect"
        val targetText = "Connect"
        createMultiColoredText(binding.title, fullText, targetText)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val adapter = ChipAdapter(viewModel.chipItems)
        binding.recyclerView.adapter = adapter

        // card view type 1
        binding.recyclerViewCard1.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val cardItems = createDummyCardItems()
        binding.recyclerViewCard1.adapter = CardTypeOneAdapter(requireContext(), cardItems)

        // card view type 2
        binding.recyclerViewCard2.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val cardItemTypeTwo = createDummyCardItemsTypeTwo()
        binding.recyclerViewCard2.adapter = CardTypeTwoAdapter(requireContext(), cardItemTypeTwo)

        // card view type 2
        binding.recyclerViewCard3.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val cardItemTypeThree = createDummyCardItemsTypeThree()
        binding.recyclerViewCard3.adapter = CardTypeThreeAdapter(requireContext(), cardItemTypeThree)
    }

    private fun createDummyCardItemsTypeThree(): List<CardItemTypeThree> {
        val cardItems = mutableListOf<CardItemTypeThree>()

        // Add dummy horizontal card items
        cardItems.add(
            CardItemTypeThree(
                R.drawable.img_destinaiton,
                "Banff",
                "Alberta, CA",
                3.5f,
                "5.0"
            )
        )
        cardItems.add(
            CardItemTypeThree(
                R.drawable.img_destinaiton,
                "Banff",
                "Alberta, CA",
                3.5f,
                "5.0"
            )
        )

        // Add more dummy horizontal card items as needed

        return cardItems
    }

    private fun createDummyCardItemsTypeTwo(): List<CardItemTypeTwo> {
        val cardItems = mutableListOf<CardItemTypeTwo>()

        // Add dummy horizontal card items
        cardItems.add(
            CardItemTypeTwo(
                R.drawable.img_destinaiton,
                "Banff",
                "Alberta, CA"
            )
        )
        cardItems.add(
            CardItemTypeTwo(
                R.drawable.img_destinaiton,
                "Banff",
                "Alberta, CA"
            )
        )

        // Add more dummy horizontal card items as needed

        return cardItems
    }

    private fun createDummyCardItems(): List<CardItem> {
        val cardItems = mutableListOf<CardItem>()

        // Add dummy card items
        cardItems.add(
            CardItem(
                R.drawable.img_destinaiton,
                "Banff",
                "Alberta, CA",
                3.5f,
                "5.0"
            )
        )
        cardItems.add(
            CardItem(
                R.drawable.img_destinaiton,
                "Banff",
                "Alberta, CA",
                3.5f,
                "5.0"
            )
        )
        cardItems.add(
            CardItem(
                R.drawable.img_destinaiton,
                "Banff",
                "Alberta, CA",
                3.5f,
                "5.0"
            )
        )

        // Add more dummy card items as needed

        return cardItems
    }

    fun createMultiColoredText(textView: TextView, fullText: String, targetText: String) {
        val spannable = SpannableString(fullText)

        val startIndex = fullText.indexOf(targetText)
        val endIndex = startIndex + targetText.length

        val color = ContextCompat.getColor(requireContext(), R.color.yellow)

        val span = ForegroundColorSpan(color)
        spannable.setSpan(span, startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        textView.text = spannable
    }
}
