

# WAFLite

[![Build Status](https://travis-ci.org/asufana/WAFLite.svg?branch=master)](https://travis-ci.org/asufana/WAFLite)

Tiny Web Application Framework for Java8.


## Examples

```java
@Test
public void test() throws Exception {

    final Integer port = 8080;
    final WAFLite waf = new WAFLite(port);
    waf.get("/hoge", (req, res) -> res.render(String.format("Hello Hoge! ( Method: %s )", req.method())));
    waf.get("/moge", (req, res) -> res.render(String.format("Hello Moge! ( Method: %s )", req.method())));

    final Server server = waf.start();
    
    final HttpResponse res01 = Http.get(port, "/hoge");
    assertThat(res01.code(), is(200));
    assertThat(res01.contents(), is("Hello Hoge! ( Method: GET )"));
    
    final HttpResponse res02 = Http.get(port, "/moge");
    assertThat(res02.code(), is(200));
    assertThat(res02.contents(), is("Hello Moge! ( Method: GET )"));
    
    server.stop();
}
```
