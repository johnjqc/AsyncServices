# PersonaService


```bash
curl -X POST "http://localhost:8080/api/v1/customers" \
  -H "Content-Type: application/json" \
  -H "X-Correlation-Id: 11111111-1111-1111-1111-111111111111" \
  -H "X-Transaction-Id: 22222222-2222-2222-2222-222222222222" \
  -d '{
    "name": "Jose Lema",
    "gender": "MALE",
    "age": 35,
    "identification": "1723456789",
    "address": "Otavalo sn y principal",
    "phone": "098254785",
    "password": "1234",
    "active": true
}'
```

```bash
curl -X GET "http://localhost:8080/api/v1/customers?page=0&size=10" \
  -H "X-Correlation-Id: 11111111-1111-1111-1111-111111111111" \
  -H "X-Transaction-Id: 22222222-2222-2222-2222-222222222222"
```

```bash
curl -X GET "http://localhost:8080/api/v1/customers/1" \
  -H "X-Correlation-Id: 11111111-1111-1111-1111-111111111111" \
  -H "X-Transaction-Id: 22222222-2222-2222-2222-222222222222"
```

```bash
curl -X PUT "http://localhost:8080/api/v1/customers/1" \
  -H "Content-Type: application/json" \
  -H "X-Correlation-Id: 11111111-1111-1111-1111-111111111111" \
  -H "X-Transaction-Id: 22222222-2222-2222-2222-222222222222" \
  -d '{
    "name": "Jose Lema Updated",
    "gender": "MALE",
    "age": 40,
    "address": "Quito Centro",
    "phone": "099999999",
    "active": true
}'
```

```bash
curl -X DELETE "http://localhost:8080/api/v1/customers/1" \
  -H "X-Correlation-Id: 11111111-1111-1111-1111-111111111111" \
  -H "X-Transaction-Id: 22222222-2222-2222-2222-222222222222"
```
