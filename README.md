# java-custom-rules
 Test case - SonarQube java custom rule
 
 ## Задание:
 написать тест в формате TDD и свое правило, которое обнаружит использование
 sun.misc.Unsafe.defineClass в исходном коде и предложит его заменить на
 java.lang.invoke.MethodHandles.Lookup.defineClass (deprecated в Java 9)


#### Файл для анализа - src/test/files/MyFirstCustomCheck.java

#### Правило - MyFirstCustomCheck.java в пакете org.sonar.samples.java.checks в src/main/java

#### Тест - MyFirstCustomCheckTest.java в пакете org.sonar.samples.java.checks в src/test/java