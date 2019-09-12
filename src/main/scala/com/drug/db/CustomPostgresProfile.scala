package com.drug.db

import com.github.tminglei.slickpg._

trait CustomPostgresProfile extends ExPostgresProfile
    with PgDateSupport
    with PgDate2Support
    with PgSprayJsonSupport {

  def pgjson = "jsonb"

  override val api = new API with DateTimeImplicits with JsonImplicits {}

}
object CustomPostgresProfile extends CustomPostgresProfile