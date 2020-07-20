package `is`.digital.interviewskeleton

import `is`.digital.interviewskeleton.data.TodoRepository
import `is`.digital.interviewskeleton.model.Todo
import `is`.digital.interviewskeleton.utils.ProgressHUD
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable


/**
 * A simple [Fragment] subclass.
 * Use the [NewTodoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewTodoFragment : Fragment() {

    private lateinit var todoRepository: TodoRepository
    private  val disposables: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val app: App = context.applicationContext as App
        todoRepository = app.todoRepository
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_todo, container, false)
    }

    override fun onStart() {
        super.onStart()

        val view: View? = getView()
        if(view == null) return

        val todoEditText = view.findViewById<EditText>(R.id.todo_description)
        val submitTodo = view.findViewById<Button>(R.id.submit_todo)

        submitTodo.setOnClickListener { v: View? ->
            val todo = todoEditText.text.toString()
            if (!todo.isEmpty()) {
                val hud = ProgressHUD.show(context)
                todoRepository.addTodo(todoEditText.text.toString())
                        ?.subscribe({ response: Todo? ->
                            hud.hide()
                            requireFragmentManager().popBackStack()
                        }) { error: Throwable? ->
                            hud.hide()
                            Log.e("NewTodo", "submit", error)
                        }?.let {
                            disposables.add(
                                    it
                        )
                        }
            }
        }

    }

    companion object {

        @JvmStatic
        fun newInstance() =
                NewTodoFragment().apply {
                    arguments = Bundle().apply {

                    }
                }
    }
}