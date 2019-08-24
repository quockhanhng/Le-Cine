package com.rikkei.tranning.le_cine.ui.personActivity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.rikkei.tranning.le_cine.R
import com.rikkei.tranning.le_cine.ui.personFragment.view.PersonDetailFragment
import com.rikkei.tranning.le_cine.util.PERSON_ID

class PersonDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_person_detail)

        if (savedInstanceState == null) {
            val extras = intent.extras
            if ((extras != null) && (extras.containsKey(PERSON_ID))) {
                val personId = extras.getString(PERSON_ID)!!
                val personDetailFragment = PersonDetailFragment.getInstance(personId)
                supportFragmentManager.beginTransaction().add(R.id.person_detail_container, personDetailFragment).commit()
            }
        }
    }
}
