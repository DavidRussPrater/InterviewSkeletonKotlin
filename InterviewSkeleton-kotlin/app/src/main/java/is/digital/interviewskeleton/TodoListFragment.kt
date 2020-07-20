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
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [TodoListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TodoListFragment : Fragment() {

    private lateinit var mainActivity: MainActivity

    private lateinit var todoRepository: TodoRepository
    private val disposables = CompositeDisposable()

    private val todoList: MutableList<Todo> = mutableListOf()
    private lateinit var recyclerView: RecyclerView;

    private var hud: ProgressHUD? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    // TODO how to implement disposables
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_todo_list, container, false)

        val context = view.context
        recyclerView = view.findViewById<View>(R.id.list) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = TodoListViewAdapter(todoList)
        recyclerView.adapter = adapter

        disposables.add(
                adapter.getItemClicks()
                        .doOnNext { todo: Todo? -> hud = ProgressHUD.show(getContext()) }
                        .flatMapCompletable { todo: Todo ->
                            todoRepository.updateTodo(todo, !todo.completed)
                                    ?.observeOn(AndroidSchedulers.mainThread())
                                    ?.doOnComplete {
                                        hud!!.hide()
                                        loadTodos()
                                    }
                                    ?.doOnError { hud!!.hide() }
                        }
                        .subscribe()
        )

        disposables.add(
                adapter.getDeleteClicks()
                        .doOnNext { todo: Todo? -> hud = ProgressHUD.show(getContext()) }
                        .flatMapCompletable { todo: Todo ->
                            todoRepository.deleteTodo(todo)
                                    ?.observeOn(AndroidSchedulers.mainThread())
                                    ?.doOnComplete {
                                        hud!!.hide()
                                        loadTodos()
                                    }
                                    ?.doOnError { hud!!.hide() }
                        }
                        .subscribe()
        )

        val fab: FloatingActionButton = view.findViewById(R.id.fab)
        fab.setOnClickListener { v: View? -> mainActivity.newTodo() }

        return view

            }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
        val app = context.getApplicationContext() as App
        todoRepository = app.todoRepository
    }

    override fun onResume() {
        super.onResume()
        // TODO call this function
      loadTodos()
    }

    companion object {

        @JvmStatic
        fun newInstance() =
                TodoListFragment().apply {
                    arguments = Bundle().apply {

                    }
                }
    }

    private fun loadTodos() {
        val hud = ProgressHUD.show(mainActivity)
        todoRepository.getTodos()
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({ todos ->
                    hud.hide()
                    todoList.clear()
                    todoList.addAll(todos)
                    recyclerView.adapter!!.notifyDataSetChanged()
                }) { error: Throwable? ->
                    hud.hide()
                    Log.e("TodoFragment", "loadTodos", error)
                }?.let {
                    disposables.add(
                            it
                    )
                }
    }

}

