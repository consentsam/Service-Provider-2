package com.laundry.user.bean


import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class PickUpStaticData{

    companion object{

        fun createDataSet(): ArrayList<PickUpTimeModel>{
            val list = ArrayList<PickUpTimeModel>()
            val cal: Calendar = Calendar.getInstance()
            // cal.setTime(yourDate)
            cal.add(Calendar.DATE, 1)
            val sdf = SimpleDateFormat("dd MMMM") //Date and time
            val sdf_ = SimpleDateFormat("EEEE")
            val cal1: Calendar = Calendar.getInstance()
            // cal.setTime(yourDate)
            cal1.add(Calendar.DATE, 2)


            val cal2: Calendar = Calendar.getInstance()
            // cal.setTime(yourDate)
            cal2.add(Calendar.DATE, 3)
            val cal3: Calendar = Calendar.getInstance()
            // cal.setTime(yourDate)
            cal3.add(Calendar.DATE, 4)
            val cal4: Calendar = Calendar.getInstance()
            // cal.setTime(yourDate)
            cal4.add(Calendar.DATE, 5)

            val cal5: Calendar = Calendar.getInstance()
            // cal.setTime(yourDate)
            cal5.add(Calendar.DATE, 6)
            val cal6: Calendar = Calendar.getInstance()
            // cal.setTime(yourDate)
            cal6.add(Calendar.DATE, 7)

            list.add(
                PickUpTimeModel(

                    sdf.format( cal.getTime()),
                    sdf_.format( cal.getTime())

                )
            )
            list.add(
                PickUpTimeModel(
                    sdf.format( cal1.getTime()),
                    sdf_.format( cal1.getTime())
                    )
                    )

            list.add(
                PickUpTimeModel(
                    sdf.format( cal2.getTime()),
                    sdf_.format( cal2.getTime())
                )
            )
            list.add(
                PickUpTimeModel(
                    sdf.format( cal3.getTime()),
                    sdf_.format( cal3.getTime())
                )
            )
            list.add(
                PickUpTimeModel(
                    sdf.format( cal4.getTime()),
                    sdf_.format( cal4.getTime())
                )
            )
            list.add(
                PickUpTimeModel(
                    sdf.format( cal5.getTime()),
                    sdf_.format( cal5.getTime())
                )
            )
            list.add(
                PickUpTimeModel(
                    sdf.format( cal6.getTime()),
                    sdf_.format( cal6.getTime())
                )
            )
            return list
        }
    }
}