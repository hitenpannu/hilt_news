package com.hitenderpannu.feature.news.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hitenderpannu.feature.news.entity.NewsArticle
import com.hitenderpannu.feature.news.ui.databinding.FragmentNewsListBinding
import com.hitenderpannu.feature.news.ui.di.DaggerComponentManager
import timber.log.Timber
import javax.inject.Inject

class NewsListFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var fragmentNewsListBinding: FragmentNewsListBinding
    private val viewModelList: NewsListFragmentViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)
            .get(NewsListFragmentViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerComponentManager.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentNewsListBinding = FragmentNewsListBinding.inflate(inflater)
        return fragmentNewsListBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fragmentNewsListBinding.newsListView.layoutManager = LinearLayoutManager(requireContext())
        viewModelList.getTopHeadlines()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModelList.newsListLiveData.observe(viewLifecycleOwner, newsListObserver)
        viewModelList.errorMessageLiveData.observe(viewLifecycleOwner, errorMessageObserver)
    }

    private val newsListObserver = Observer<List<NewsArticle>?> {
        Timber.e("NewsListSize: ${it?.size ?: 0}")
    }

    private val errorMessageObserver = Observer<String?> { errorMessage ->
        if (errorMessage.isNullOrEmpty()) return@Observer
        Timber.e(errorMessage)
    }
}
