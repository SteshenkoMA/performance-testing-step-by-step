package com.chains

import com.requests.{AddItemToCart, GetCartInfo, GetCategories, GetCategoryProducts, GetIndexPage, SubmitPurchase, UpdateQuantity}
import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder

object OrderCreationChain {
  val execute: ChainBuilder = exec(
    exitBlockOnFail{
      exec(
        GetIndexPage.getIndexPage("01_getIndexPage"),
        GetCategories.getCategories("02_getCategories"),
        GetCategoryProducts.getCategoryProducts("03_getCategoryProducts"),
        AddItemToCart.addItemToCart("04_addItemToCart"),
        GetCategoryProducts.getCategoryProducts("05_getCategoryProducts"),
        AddItemToCart.addItemToCart("06_addItemToCart"),
        GetCartInfo.getCartInfo("07_getCartInfo"),
        UpdateQuantity.updateQuantity("08_updateQuantity"),
        GetCartInfo.getCartInfo("09_getCartInfo"),
        SubmitPurchase.submitPurchase("10_submitPurchase")
      )
    }
  )

/*
  .exec(
    session => {
      println("account: " + session("account").as[String])
      )
      session
    })
*/

}