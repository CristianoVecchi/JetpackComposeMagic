package com.cristianovecchi.jetpackcomposemagic

// General Constants
class G {
 companion object{
     const val quarter: Long = 96
     const val totalPieceTickDuration: Long = 96 * 4 * 64

     @JvmStatic
     fun convertIntegers(integers: List<Int>): IntArray? {
         val ret = IntArray(integers.size)
         val iterator = integers.iterator()
         for (i in ret.indices) {
             ret[i] = iterator.next()
         }
         return ret
     }



 }

}