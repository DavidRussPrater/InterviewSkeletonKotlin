package `is`.digital.interviewskeleton.utils

import android.content.Context
import com.kaopiz.kprogresshud.KProgressHUD

class ProgressHUD private constructor(private val hud: KProgressHUD) {
    fun hide() {
        hud.dismiss()
    }

    companion object {
        fun show(context: Context?): ProgressHUD {
            return ProgressHUD(
                    KProgressHUD.create(context)
                            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                            .show()
            )
        }
    }

}