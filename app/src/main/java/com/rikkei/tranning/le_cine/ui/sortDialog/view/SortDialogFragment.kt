package com.rikkei.tranning.le_cine.ui.sortDialog.view

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import com.rikkei.tranning.le_cine.App
import com.rikkei.tranning.le_cine.R
import com.rikkei.tranning.le_cine.ui.listFragment.presenter.MoviesListPresenter
import com.rikkei.tranning.le_cine.ui.sortDialog.presenter.SortDialogPresenter
import javax.inject.Inject

class SortDialogFragment : DialogFragment(), SortDialogView, RadioGroup.OnCheckedChangeListener {

    @Inject
    lateinit var presenter: SortDialogPresenter

    private lateinit var moviesListPresenter: MoviesListPresenter
    private lateinit var sortingGroup: RadioGroup
    private lateinit var rbMostPopular: RadioButton
    private lateinit var rbHighestRated: RadioButton
    private lateinit var rbNewest: RadioButton
    private lateinit var rbFavourite: RadioButton

    companion object {
        fun newInstance(moviesListPresenter: MoviesListPresenter): SortDialogFragment {
            val fragment = SortDialogFragment()
            fragment.moviesListPresenter = moviesListPresenter
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        retainInstance = true
        (activity!!.application as App).getListComponent()?.inject(this)
        presenter.setView(this)
    }

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val inflater = activity!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val dialogView = inflater.inflate(R.layout.sorting_option, null)

        initViews(dialogView)

        return Dialog(activity!!).apply {
            setContentView(dialogView)
            setTitle("Sort By")
            show()
        }
    }

    private fun initViews(dialogView: View) {
        sortingGroup = dialogView.findViewById(R.id.sorting_group)
        rbMostPopular = dialogView.findViewById(R.id.most_popular)
        rbHighestRated = dialogView.findViewById(R.id.highest_rated)
        rbNewest = dialogView.findViewById(R.id.newest)
        rbFavourite = dialogView.findViewById(R.id.favourite)
        presenter.setLastSavedOption()
        sortingGroup.setOnCheckedChangeListener(this)
    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        when (checkedId) {
            R.id.most_popular -> {
                presenter.onPopularMoviesSelected()
                moviesListPresenter.firstPage()
            }
            R.id.highest_rated -> {
                presenter.onHighestRatedMoviesSelected()
                moviesListPresenter.firstPage()
            }
            R.id.newest -> {
                presenter.onNewestMoviesSelected()
                moviesListPresenter.firstPage()
            }
            R.id.favourite -> {
                presenter.onFavouriteMoviesSelected()
                moviesListPresenter.firstPage()
            }
        }
    }

    override fun setPopularChecked() {
        rbMostPopular.isChecked = true
    }

    override fun setHighestRatedChecked() {
        rbHighestRated.isChecked = true
    }

    override fun setNewestChecked() {
        rbNewest.isChecked = true
    }

    override fun setFavouriteChecked() {
        rbFavourite.isChecked = true
    }

    override fun dismissDialog() {
        dismiss()
    }

    override fun onDestroy() {
        super.onDestroy()

        presenter.destroy()
    }
}