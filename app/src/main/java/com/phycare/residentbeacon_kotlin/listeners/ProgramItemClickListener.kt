package com.phycare.residentbeacon_kotlin.listeners

import com.phycare.residentbeacon_kotlin.ui.programs.ProgramCompSearch

interface ProgramItemClickListener {
    fun onItemClick(programSearch: Int, programCompSearch: ProgramCompSearch)
}