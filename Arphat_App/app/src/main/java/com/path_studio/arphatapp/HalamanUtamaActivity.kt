package com.path_studio.arphatapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.iammert.library.readablebottombar.ReadableBottomBar
import kotlinx.android.synthetic.main.activity_halaman_utama.*

class HalamanUtamaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_halaman_utama)

        //set fragment jadi home
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container,  HomeFragment()).commit()

        bottomBar.setOnItemSelectListener( object : ReadableBottomBar.ItemSelectListener{
            override fun onItemSelected(index: Int) {
                when(index){
                    0->{
                        //untuk home
                        val transaction = supportFragmentManager.beginTransaction()
                        transaction.replace(R.id.fragment_container,  HomeFragment()).commit()
                    }

                    1->{
                        //untuk history
                    }
                    2->{
                        //untuk payment
                    }

                    3->{
                        //untuk inbox
                    }

                    4->{
                        //untuk account
                        val transaction = supportFragmentManager.beginTransaction()
                        transaction.replace(R.id.fragment_container,  AccountFragment()).commit()
                    }
                }
            }
        })

    }
}

//        switch (view.getId()) {
//            case R.id.menuHome:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                        new HomeFragment()).commit();
//                setMenuActive("Home");
//                break;
//            case R.id.menuHistory:
//                setMenuActive("History");
//                break;
//            case R.id.menuPayment:
//                setMenuActive("Payment");
//                break;
//            case R.id.menuInbox:
//                setMenuActive("Inbox");
//                break;
//            case R.id.menuAccount:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                        new AccountFragment()).commit();
//                setMenuActive("Account");
//                break;
