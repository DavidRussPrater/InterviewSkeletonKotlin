package `is`.digital.interviewskeleton

import `is`.digital.interviewskeleton.TodoListFragment.Companion.newInstance
import android.os.Bundle
import android.util.Log
import android.widget.BaseAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private val tasks = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        supportActionBar?.title = "TODO!"
        if (savedInstanceState != null) return
        val fragment = newInstance()
        supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, fragment)
                .commit()
    }

    fun newTodo() {
        val fragment = NewTodoFragment()
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .addToBackStack(null)
                .commit()
    }


//    fun loadTodos() {
//        val url = QuickUtils.HOST + "/task"
//        QuickUtils.getResponse(this, url, object : QuickUtils.QuickCallbackInterface {
//            override fun onSuccess(response: String?) {
//                try {
//                    val root = JSONObject(response)
//                    if (root.getInt("count") > 0) {
//                        val data = root.getJSONArray("data")
//                        for (i in 0 until data.length()) {
//                            val task = data.getJSONObject(i)
//                            tasks.add(task.getString("description"))
//                        }
//                    }
//                    (listView.adapter as BaseAdapter).notifyDataSetChanged()
//                } catch (e: JSONException) {
//                    e.printStackTrace()
//                }
//            }
//
//            override fun onError(e: Exception) {
//                Log.d("Network", e.localizedMessage)
//            }
//        })
//    }

}
