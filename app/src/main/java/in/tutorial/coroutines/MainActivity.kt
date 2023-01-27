package `in`.tutorial.coroutines

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    var customProgressDialog:Dialog? = null
    private fun cancelProgressDialog(){
        customProgressDialog?.let {
            customProgressDialog?.dismiss()
            customProgressDialog = null
        }
    }
    private fun customProgressDialogFunction(){
        customProgressDialog = Dialog(this)
        customProgressDialog?.setContentView(R.layout.dialog_custom_progress)
        customProgressDialog?.show()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnExe:Button = findViewById(R.id.button)
        btnExe.setOnClickListener {
            customProgressDialogFunction()
            lifecycleScope.launch {
                execute("Task executed")
            }
        }
    }
    private suspend fun execute(result: String){
        withContext(Dispatchers.IO){
            for(i in 1..100000){
                Log.e("delay","delay : "+i)
            }
            runOnUiThread{
                cancelProgressDialog()
                Toast.makeText(this@MainActivity, result, Toast.LENGTH_LONG).show()
            }
        }
    }
}