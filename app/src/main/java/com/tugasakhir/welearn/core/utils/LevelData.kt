package com.tugasakhir.welearn.core.utils
//
//import com.tugasakhir.welearn.domain.model.Level
//
//object LevelData {
//
//    private val level = arrayOf(
//        0,1,2,3,4,
//    )
//
//    private val name = arrayOf(
//        "level 0","level 1","level 2","level 3","level 4"
//    )
//
//    val listLevel: ArrayList<Level>
//        get() {
//            val list = arrayListOf<Level>()
//            for(position in level.indices) {
//                val lvl = Level()
//                lvl.id_level = level[position]
//                lvl.level_soal = name[position]
//                list.add(lvl)
//            }
//            return list
//        }
//
//    private val levelHuruf = arrayOf(
//        0,1,2,3
//    )
//
//    private val nameLevelHuruf = arrayOf(
//        "level 0","level 1","level 2","level 3"
//    )
//
//    val listLevelHuruf: ArrayList<Level>
//        get() {
//            val list = arrayListOf<Level>()
//            for(position in levelHuruf.indices) {
//                val lvl = Level()
//                lvl.id_level = levelHuruf[position]
//                lvl.level_soal = nameLevelHuruf[position]
//                list.add(lvl)
//            }
//            return list
//        }
//}