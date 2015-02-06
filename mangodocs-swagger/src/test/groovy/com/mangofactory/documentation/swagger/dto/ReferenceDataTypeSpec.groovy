package com.mangofactory.documentation.swagger.dto

class ReferenceDataTypeSpec extends InternalJsonSerializationSpec {

  def "should serialize"() {
    expect:
      writePretty(new ReferenceDataType('Pet')) == '''{
  "type" : "Pet"
}'''
  }
}