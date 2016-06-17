package conf

import com.typesafe.config.ConfigFactory

object DBConfig {

  val config = ConfigFactory.load()

  val url = config.getString("oracle.url")
  val driver = config.getString("oracle.driver")
  val username = config.getString("oracle.username")
  val password = config.getString("oracle.password")

}
