package com.example.testkotlin3

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.testkotlin3.databinding.ActivityMainBinding
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor
import io.flutter.plugin.common.MethodChannel
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    lateinit var flutterEngine : FlutterEngine
    lateinit var channel : MethodChannel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

//        binding.fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }


        binding.fab.setOnClickListener {
            flutterEngine = FlutterEngine(this)

            flutterEngine.navigationChannel.setInitialRoute("/")

            flutterEngine.dartExecutor.executeDartEntrypoint(
                DartExecutor.DartEntrypoint.createDefault()
            )

            FlutterEngineCache.getInstance().put("chatbot_engine",flutterEngine)

            channel = MethodChannel(flutterEngine.dartExecutor,"com.serina.bot/chatbot")

            channel.setMethodCallHandler { call, result ->
                when (call.method) {
                    "getNd" -> {
                        // call method to getIdUser and return id user
                        val idUser = "131183155056"
                        result.success(idUser)
                    }
                    "getUserId" -> {
                        val id = "097caoah097vga08sgdas"
                        result.success(id)
                    }
                    "getPayload" -> {
                        val jsonObject = JSONObject()
                        jsonObject.put("nd","143592102777")
                        jsonObject.put("appId","app-chatbot-demo")
                        jsonObject.put("appSecret","7v3zCtPyGy4log2YPGAQ")
                        jsonObject.put("userId","09ags097ga08vas7gd")
                        result.success(jsonObject.toString())
                    }
                }
            }
            val intent = FlutterActivity.withCachedEngine("chatbot_engine").build(this)
            startActivity(intent)
//            startActivity(FlutterActivity.withCachedEngine("/").build(this))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

}