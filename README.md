# Endpoint Control using Configuration

## Description
- Control all endpoint using rights.
- Multiple url-pattern can have multiple rights.
- In case of url does not match, need to go for higher level of url as well.


## Execution Process

### Initialization
- Load url-pattern and rights from properties file.
- Get all defined url mapping from `org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping` in validPaths.
- Normalize url path by appending `/` in prefix and suffix.
- Load rights from class property descriptors using `java.beans.Introspector`.


### Validation
- check url-pattern and rights is not null or empty.
- check validPaths is not null or empty.
- check url-pattern is in validPaths.
- check rights is in class properties.


### Check Access
- normalize requesting endpoint url.
- get miscellaneous rights from context.
- verify requesting endpoint url with configured url pattern.
- check whether rights is enabled or not using `org.apache.commons.beanutils.PropertyUtils.getProperty(...)`.
- if above condition does't satisfy then remove the last path from endpoint url and repeat the above steps.

example :
endpoint-url : /product/detail
by remove last endpoint url : /product
by remove last endpoint url : /


## Note
- url-pattern can be verified using following ways:
    - starts with
    - contains
    - ends with
- to make endpoint url consistent, normalization can be done:
    - append `/` in the beginning
    - append `/` in the ending
- request can be made only for `/` only, if this is case we might need to skip it.