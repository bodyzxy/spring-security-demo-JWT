# 基于Spring Security6.X版本实现验证

### 为什么使用

Spring Security 6引入了很多破坏性的更新，包括废弃代码的删除，方法重命名
，全新的配置DSL等。

### 配置
```angular2html
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt-api</artifactId>
            <version>0.11.5</version>
        </dependency>

        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt-impl</artifactId>
            <version>0.11.5</version>
            <scope>runtime</scope>
        </dependency>

        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt-jackson</artifactId>
            <version>0.11.5</version>
            <scope>runtime</scope>
        </dependency>
```

### 实现

首先通过Filter实现安全检验

```angular2html
public class AuthTokenFilter extends OncePerRequestFilter
```

这里通过继承`OncePerRequestFilter`实现`doFilterInternal`以达到
对请求头的校验

同时需要自己编写令牌解析和创建也就是`JwtUtils`类，这个类用来创建
token和解析token

然后进行全局拦截以及JWT设置在`WebSecurityConfig`中，首先配置拦截
地址，以及错误处理和设置会话创建策略。

```java
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/api/user/**").permitAll()
                                .anyRequest().authenticated()
                )
                .exceptionHandling(exception -> exception.authenticationEntryPoint(authEntryPointJwt))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        ;
        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
```

### 注意


因为传递的Role是HashSet类型，所以需要自己定义反序列化

```java
 public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Set.class, new StringSetDeserializer());
        objectMapper.registerModule(module);
        return objectMapper;
    }
```

其中`StringSetDeserializer`是自定义反序列化

### 使用

```java
Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword())
            );


            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
```

其中`authenticationManager.authenticate`直接验证用户和密码，并将
认证状态和用户信息封装到`Authentication`中。然后为其生成令牌，`userDetails.getAuthorities()` 
返回的是包含用户角色信息的集合，通过 stream() 和 map() 操作将`GrantedAuthority`对象中的角色名提取出来，并以列表形式返回。


