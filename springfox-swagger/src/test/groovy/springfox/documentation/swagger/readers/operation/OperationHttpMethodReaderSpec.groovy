/*
 *
 *  Copyright 2015 the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *
 */

package springfox.documentation.swagger.readers.operation

import org.springframework.web.bind.annotation.RequestMethod
import springfox.documentation.spring.web.mixins.RequestMappingSupport
import springfox.documentation.spring.web.plugins.DocumentationContextSpec
import springfox.documentation.builders.OperationBuilder
import springfox.documentation.spi.service.contexts.OperationContext

@Mixin([RequestMappingSupport])
class OperationHttpMethodReaderSpec extends DocumentationContextSpec {
  def "should return api method annotation when present"() {

    given:
      OperationContext operationContext = new OperationContext(new OperationBuilder(),
              currentHttpMethod, handlerMethod, 0, requestMappingInfo("/somePath"),
              context(), "/anyPath")

      OperationHttpMethodReader operationMethodReader = new OperationHttpMethodReader();
    when:
      operationMethodReader.apply(operationContext)
    and:
      def operation = operationContext.operationBuilder().build()

    then:
      operation.method == expected
    where:
      currentHttpMethod  | handlerMethod                                     | expected
      RequestMethod.GET  | dummyHandlerMethod()                              | null
      RequestMethod.PUT  | dummyHandlerMethod()                              | null
      RequestMethod.POST | dummyHandlerMethod('methodWithHttpGETMethod')     | 'GET'
      RequestMethod.POST | dummyHandlerMethod('methodWithInvalidHttpMethod') | null
  }
}
