
        package com.isuwang.soa.scala.service

import com.isuwang.dapeng.core.{Processor, Service}
        import com.isuwang.dapeng.core.SoaGlobalTransactional

        /**
         * Autogenerated by Dapeng-Code-Generator (1.2.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated

        * 
        **/
        @Service(name ="com.isuwang.soa.service.CalculateService" , version = "1.0.0")
        @Processor(className = "com.isuwang.soa.scala.CalculateServiceCodec$Processor")
        trait CalculateService {
        
            /**
            * 
            **/
            
            @throws[com.isuwang.dapeng.core.SoaException]
            def calcualteWordCount(
            filename: String ,word: String ): Int

          
            /**
            * 
            **/
            
            @throws[com.isuwang.dapeng.core.SoaException]
            def calcualteWordsCount(
            fileName: String ): Map[String, Int]

          
        }
        