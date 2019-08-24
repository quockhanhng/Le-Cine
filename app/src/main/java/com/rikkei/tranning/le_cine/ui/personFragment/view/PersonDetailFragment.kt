package com.rikkei.tranning.le_cine.ui.personFragment.view

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.rikkei.tranning.le_cine.App
import com.rikkei.tranning.le_cine.R
import com.rikkei.tranning.le_cine.model.Person
import com.rikkei.tranning.le_cine.ui.personFragment.presenter.PersonDetailPresenter
import com.rikkei.tranning.le_cine.util.PERSON_ID
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_person_detail.*
import kotlinx.android.synthetic.main.person_detail_fact.*
import java.text.ParseException
import java.text.SimpleDateFormat
import javax.inject.Inject
import android.text.style.UnderlineSpan
import android.text.SpannableString

class PersonDetailFragment : Fragment(), PersonDetailView, View.OnClickListener {

    @Inject
    lateinit var presenter: PersonDetailPresenter

    private lateinit var person: Person

    companion object {
        fun getInstance(personId: String): PersonDetailFragment {
            val args = Bundle()
            args.putString(PERSON_ID, personId)
            val movieDetailsFragment = PersonDetailFragment()
            movieDetailsFragment.arguments = args
            return movieDetailsFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        retainInstance = true
        setHasOptionsMenu(true)
        (activity?.application as App).createPersonDetailComponent().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_person_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolBar()

        if (arguments != null) {
            val personId = arguments!!.getString(PERSON_ID)!!

            presenter.setView(this)
            presenter.showInfo(personId)
        }
    }

    private fun setToolBar() {
        collapsing_toolbar?.setContentScrimColor(ContextCompat.getColor(context!!, R.color.colorPrimary))
        collapsing_toolbar?.setCollapsedTitleTextAppearance(R.style.CollapsedToolbar)
        collapsing_toolbar?.setExpandedTitleTextAppearance(R.style.ExpandedToolbar)
        collapsing_toolbar?.isTitleEnabled = true

        if (toolbar != null) {
            (activity as AppCompatActivity).setSupportActionBar(toolbar)

            val actionBar = (activity as AppCompatActivity).supportActionBar
            actionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    @SuppressLint("SimpleDateFormat")
    override fun showInfo(person: Person) {

        this.person = person

        collapsing_toolbar?.title = person.name

        if (person.profilePath != null) {
            Picasso.get()
                .load(person.getProfileUrl())
                .into(person_detail_poster)
        } else {
            person_detail_poster.setImageResource(R.drawable.ic_default_profile)
        }
        person_detail_name.text = person.name
        person_detail_bio.text =
            if (person.biography == "") "Unfortunately, biography of the actor/actress has not been provided." else person.biography

        person_detail_born.text = person.birthday ?: "N/A"
        person_detail_died.text = person.deathDay ?: "N/A"
        person_detail_birthplace.text = person.birthPlace ?: "N/A"

        if (person.homePage == null)
            person_detail_homepage.text = "N/A"
        else {
            val homePage = person.homePage
            val content = SpannableString(homePage)
            content.setSpan(UnderlineSpan(), 0, homePage.length, 0)
            person_detail_homepage.text = content
            person_detail_homepage.setTextColor(Color.parseColor("#0099cc"))
            person_detail_homepage.setOnClickListener(this)
        }

        person_detail_also_known_as.text = if (person.alsoKnowAs.isNotEmpty())
            person.alsoKnowAs.toString().substring(1, person.alsoKnowAs.toString().length - 1)
        else "N/A"

        if (person.birthday != null) {
            val format = SimpleDateFormat("yyyy-MM-dd")
            try {
                val date = format.parse(person.birthday)
                val diff = if (person.deathDay != null) {
                    (format.parse(person.deathDay).time - date.time) / (24 * 60 * 60 * 1000) / 365
                } else {
                    (System.currentTimeMillis() - date.time) / (24 * 60 * 60 * 1000) / 365
                }
                person_detail_age.text = diff.toString()
            } catch (e: ParseException) {
                Log.e("ERROR", e.toString())
            }
        } else person_detail_age.text = "N/A"
    }

    override fun showError(message: String) {
        Log.e("ERROR", message)
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.person_detail_homepage) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(person.homePage))
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        (activity?.application as App).releasePersonDetailComponent()
    }
}