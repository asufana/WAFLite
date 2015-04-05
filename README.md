

# WAFLite

[![Build Status](https://travis-ci.org/asufana/WAFLite.svg?branch=master)](https://travis-ci.org/asufana/WAFLite)

A Sinatra-like simple Web Application Framework for Java8.

## Examples

#### Application code

```java
public class WebApp extends WAFLite {
    
    public WebApp(final Integer port) {
        super(port);
    }
    
    @Override
    public Server start() {
        
        # set routing
        get("/hoge", (req, res) -> res.render(String.format("Hello Hoge! ( Method: %s )", req.method())));
        get("/moge", (req, res) -> res.render(String.format("Hello Moge! ( Method: %s )", req.method())));
        
        return super.start();
    }
}
```

#### Test code

```java
@Test
public void test() throws Exception {

    final Integer port = 8080;
    final Server server = new WebApp(port).start();
    
    final HttpResponse res01 = Http.get(port, "/hoge");
    assertThat(res01.code(), is(200));
    assertThat(res01.contents(), is("Hello Hoge! ( Method: GET )"));
    
    final HttpResponse res02 = Http.get(port, "/moge");
    assertThat(res02.code(), is(200));
    assertThat(res02.contents(), is("Hello Moge! ( Method: GET )"));
    
    server.stop();
}
```
