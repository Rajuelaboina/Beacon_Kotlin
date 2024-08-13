package com.phycare.residentbeacon_kotlin.utils

import androidx.recyclerview.widget.RecyclerView

class CustomOnScrollListener : RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (dy > 10) {
            // fab.hide()
            KeyBoardClose().closeKeyboard(recyclerView.rootView)
        }
        // if the recycler view is scrolled
        // above extend the FAB
        // if the recycler view is scrolled
        // above extend the FAB
        if (dy < -10) {
            // fab.show()
            KeyBoardClose().closeKeyboard(recyclerView.rootView)
        }
        // of the recycler view is at the first
        // item always extend the FAB
        // of the recycler view is at the first
        // item always extend the FAB
        if (!recyclerView.canScrollVertically(-1)) {
            // fab.show()
        }
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)

    }
}