**Layihə Haqqında**

E-Shop layihəsi müasir bir onlayn alış-veriş platformasıdır, istifadəçilərə müxtəlif məhsul kateqoriyalarında rahat alış-veriş təcrübəsi təqdim edir.
Layihə Spring Boot texnologiyası ilə qurulmuşdur və istifadəçilərin asanlıqla məhsul axtarışı, seçimi və sifarişi etməsini təmin edir.
Sistemdə hər məhsul üçün fərqli ölçü və rəng variantları mövcuddur, beləliklə müştərilər öz istəklərinə uyğun məhsulu asanlıqla seçə bilərlər.
E-Shop layihəsində iki əsas istifadəçi tipi mövcuddur: Müştəri (Customer) və İşçi (Employee). Müştəri istifadəçiləri yalnız alış-veriş edə bilər
və onlar üçün sadə qeydiyyat və giriş API-ləri mövcuddur. İşçi istifadəçiləri isə müxtəlif rollara sahibdir: SUPERADMIN, ADMIN və OPERATOR,
bu da onlara sistemdə müxtəlif hüquq və səlahiyyətlər verir. İstifadəçilərin təhlükəsizliyi təmin edilib, müştəri və işçi üçün fərqli
autentifikasiya və avtorizasiya sistemləri mövcuddur. Bu layihə monolitik bir tətbiq olaraq qurulmuşdur, amma gələcəkdə daha çevik və genişlənə
bilən arxitektura üçün mikrosistemlərə keçid planlaşdırılır. Layihə, Spring Boot'un gücündən istifadə edərək yüksək performans və genişlənə bilən
bir platforma təmin edir.
```
**Funksionallıqlar**
1.Məhsul İdarəetməsi
Məhsul əlavə et, yenilə, sil.
Məhsulun ölçü və rəng variantlarını idarə et.
2.Sifariş İdarəetməsi
Müştəri məhsulları səbətə əlavə edə, sifariş edə bilər.
Sifarişinin statusu izləyə bilər.
3.İstifadəçi İdarəetməsi
Müştəri qeydiyyatı və girişi.
İşçilər üçün SUPERADMIN, ADMIN və OPERATOR rolları ilə fərqli hüquqlar.
4.Təhlükəsizlik və Authentication
JWT token əsaslı autentifikasiya.
Müştəri və işçi üçün ayrılmış API-lər.
5.Global Exception Handling
Sistemdə baş verən səhvlərin mərkəzləşdirilmiş idarə edilməsi.

**Texnologiyalar və kitabxanalar**
Java 17: Əsas proqramlaşdırma dili.
Spring Boot 3.3.2: Tətbiqin sürətli inkişafı və asan konfiqurasiyası üçün istifadə olunan Java əsaslı framework.
Spring Data JPA: ORM və verilənlər bazası əməliyyatları üçün.
Spring Security: Təhlükəsizlik və autentifikasiya üçün.
JWT (io.jsonwebtoken): JSON Web Token əsaslı autentifikasiya.
Redis: Keşləmə üçün verilənlər bazası.
MapStruct: DTO-ların avtomatik map edilməsi üçün.
Lombok: Kodun optimallaşdırılması və təkrarı azaltmaq üçün.
SpringDoc OpenAPI: API sənədləşdirməsi üçün.
Log4j2: Loglama.
Oracle JDBC (ojdbc11): Oracle verilənlər bazası ilə əlaqə üçün.

**Modul Dizaynı**
src
├── main
│   ├── java
│   │   └── az.orient.eshop
│   │       ├── config             # Konfiqurasiya sinifləri (məsələn, verilənlər bazası, təhlükəsizlik və s.).
│   │       ├── controller         # HTTP sorğularını qəbul edən və xidmət metodlarına yönləndirən siniflər.
│   │       ├── dto                # Gələn və gedən məlumatlar üçün Data Transfer Obje (DTO).
│   │       │   ├── request        # Gələn məlumatlar üçün DTO-lar (məsələn, POST/PUT sorğularının bədənləri).
│   │       │   └── response       # Gedən məlumatlar üçün DTO-lar (məsələn, GET/POST sorğularının cavabları).
│   │       ├── entity             # Verilənlər bazasının entitiləri (cədvəllər).
│   │       ├── enums              # Layihədə istifadə olunan enum tipləri.
│   │       ├── exception          # Xətaların idarə edilməsi üçün xüsusi istisna sinifləri.
│   │       ├── mapper             # MapStruct və ya digər obyekt çevirmə lojiği üçün siniflər.
│   │       ├── repository         # Verilənlər bazası ilə əlaqə yaratmaq üçün interfeyslər, JPA repository-dən irs alır.
│   │       ├── security           # Təhlükəsizlik konfiqurasiyaları, autentifikasiya və avtorizasiya lojiği.
│   │       ├── service            # İş lojiğini idarə edən xidmət sinifləri.
│   │       └── validation         # Giriş verilənlərinin yoxlanması üçün xüsusi doğrulama lojiği və annotasiyalar.
│   └── resources
│       ├── application.properties # Tətbiq konfiqurasiya faylı (məsələn, verilənlər bazası, təhlükəsizlik və s.)
│       └── log4j2.xml             # Tətbiqin loqlarını idarə etmək üçün log4j konfiqurasiyası..
└── test
└── java
└── az.orient.eshop        # Unit və inteqrasiya testləri üçün siniflər.

Entity-lər:
Employee, Customer, Product, Order, ProductImage, Brand, Cart, Category, Color, OrderStatus, Payment, ProductDetails,
ProductVideo, Shelf, ShelfProductDetails, Size, Subcategory,Warehouse, WarehouseWork, Wishlist siniflərindən ibarətdir.

Repository-lər:
Database əməliyyatları üçün istifadə edilir.

Service və ServiceImpl:
Biznes loqikası buradadır.

Controller-lər:
REST API interfeysi yaradılır
.
GlobalException:
Sistemdə exception handling ünvanlı təmin edilir.

Swagger UI
API-lər OpenAPI vasitəsilə dokumentasiya olunub. Swagger interfeysi étibarlı REST API test etməyə imkan yaradar.

**API-lər**
BrandController
POST /brands: Yeni brand əlavə etmək.
GET /brands: Bütün brand-ların siyahısını almaq.
GET /brands/{id}: Müəyyən bir brand haqqında məlumat əldə etmək.
PUT /brands/{id}: Mövcud brand-u yeniləmək.
DELETE /brands/{id}: Brand-u silmək.

CartController
GET /carts: Müştərinin səbətindəki məhsuları əldə etmək.
POST /carts/add/{productDetailsId}: Məhsul əlavə etmək.
DELETE /carts/delete/{productDetailsId}: Məhsul silmək.

CategoryController
POST /categories: Yeni kateqoriya əlavə etmək.
GET /categories: Bütün kateqoriyaların siyahısını almaq.
GET /categories/{id}: Müəyyən bir kateqoriya haqqında məlumat əldə etmək.
PUT /categories/{id}: Mövcud kateqoriyanı yeniləmək.
DELETE /categories/{id}: Kateqoriyanı silmək.
ColorController

POST /colors: Yeni rəng əlavə etmək.
GET /colors: Bütün rənglərin siyahısını almaq.
GET /colors/{id}: Müəyyən bir rəng haqqında məlumat əldə etmək.
PUT /colors/{id}: Mövcud rəngi yeniləmək.
DELETE /colors/{id}: Rəngi silmək.

CustomerAuthController
POST /auth/customer/login: Müştəriyə giriş imkanı vermək.
POST /auth/customer/logout: Müştəri çıxışı (Authorization token ilə).

CustomerController
POST /customers/register: Yeni müştəri qeydiyyatı.
GET /customers: Bütün müştərilərin siyahısını almaq.
GET /customers/{id}: Müəyyən bir müştəri haqqında məlumat əldə etmək.
PUT /customers/{id}: Müştəri məlumatlarını yeniləmək.
DELETE /customers/{id}: Müştəri silmək.

EmployeeAuthController
POST /auth/employee/login: İşçi üçün giriş API-si.
POST /auth/employee/logout: İşçi çıxışı (Authorization token ilə).

EmployeeController
POST /employees: Yeni işçi əlavə etmək.
GET /employees: Bütün işçilərin siyahısını almaq.
GET /employees/{id}: Müəyyən bir işçi haqqında məlumat əldə etmək.
PUT /employees/{id}: İşçi məlumatlarını yeniləmək.
DELETE /employees/{id}: İşçini silmək.

ImageController
POST /images/{productDetailsId}: Məhsula şəkil əlavə etmək.
DELETE /images/list/{productDetailsId}: Məhsulun şəkillərini silmək.
DELETE /images/{imageId}: Şəkili silmək.
GET /images/list/{productDetailsId}: Məhsulun şəkillərini əldə etmək.
GET /images/{imageId}: Müəyyən bir şəkili əldə etmək.

OrderController
GET /orders: Müştərinin sifarişlərini əldə etmək.
OrderStatusController
GET /orderstatus/{orderId}: Sifariş statusunu almaq.

PaymentController
POST /payment/{paymentMethod}: Ödəniş etmək.

ProductController
POST /products: Yeni məhsul əlavə etmək.
GET /products: Bütün məhsulların siyahısını almaq.
GET /products/{id}: Müəyyən bir məhsul haqqında məlumat əldə etmək.
PUT /products/{id}: Məhsulu yeniləmək.
DELETE /products/{id}: Məhsulu silmək.

ProductDetailsController
POST /productdetails: Yeni məhsulun detalları əlavə etmək.
PUT /productdetails/{id}: Məhsul detalları yeniləmək.
GET /productdetails: Bütün məhsul detalları siyahısını almaq.
GET /productdetails/{id}: Müəyyən bir məhsulun detalını əldə etmək.
DELETE /productdetails/{id}: Məhsul detalını silmək.

ShelfController
POST /shelfs: Yeni rəf əlavə etmək.
GET /shelfs: Bütün rəflərin siyahısını almaq.
GET /shelfs/{id}: Müəyyən bir rəf haqqında məlumat əldə etmək.
PUT /shelfs/{id}: Rəfi yeniləmək.
DELETE /shelfs/{id}: Rəfi silmək.

ShelfProductDetailsController
POST /shelfproducts: Məhsulu rəfə əlavə etmək.
DELETE /shelfproducts: Məhsulu rəfdən silmək.

SizeController
POST /size: Yeni ölçü əlavə etmək.
GET /size: Bütün ölçülərin siyahısını almaq.
GET /size/{id}: Müəyyən bir ölçü haqqında məlumat əldə etmək.
PUT /size/{id}: Ölçünü yeniləmək.
DELETE /size/{id}: Ölçünü silmək.

SubcategoryController
POST /subcategorys: Yeni subkateqoriya əlavə etmək.
GET /subcategorys: Bütün subkateqoriyaların siyahısını almaq.
GET /subcategorys/{id}: Müəyyən bir subkateqoriya haqqında məlumat əldə etmək.
PUT /subcategorys/{id}: Subkateqoriyanı yeniləmək.
DELETE /subcategorys/{id}: Subkateqoriyanı silmək.

VideoController
POST /videos/{productDetailsId}: Məhsul detalları ilə videolar əlavə etmək.
DELETE /videos/list/{productDetailsId}: Məhsul detalları ilə videoları silmək.
DELETE /videos/{videoId}: Müəyyən bir videonu silmək.
GET /videos/list/{productDetailsId}: Məhsul detalları ilə videoları əldə etmək.
GET /videos/{videoId}: Müəyyən bir videonu əldə etmək.

WarehouseController
POST /warehouses: Yeni anbar əlavə etmək.
GET /warehouses: Bütün anbarların siyahısını almaq.
GET /warehouses/{id}: Müəyyən bir anbar haqqında məlumat əldə etmək.
PUT /warehouses/{id}: Anbarı yeniləmək.
DELETE /warehouses/{id}: Anbarı silmək.

WarehouseWorkController
GET /warehouseworks: Bütün anbar işlərini almaq.
PUT /warehouseworks/handlework/{id}: Anbar işini idarə etmək.

WishlistController
GET /wishlists: Müştərinin bütün arzuladığı məhsulları almaq.
POST /wishlists: Məhsulu arzu siyahısına əlavə etmək.
DELETE /wishlists: Məhsulu arzu siyahısından silmək.

**Layihəni İşlətmək**
Tələblər:
JDK 17
Maven 3.8+
Oracle Database
Adımlar:
1.Konfiqurasiya Edin
Layihədə application.properties faylını konfiqurasiya edərək verilənlər bazası, Redis və JWT üçün lazımi məlumatları təmin edin.
2.Layihəni Klonlayın
git clone https://github.com/NematMamiyev/Eshop.git
cd eshop
3.Maven Asılılıqlarını Yükləyin
mvn clean install
4.Layihəni işə Salın
mvn spring-boot:run
5.Sistemə Swagger vasitəsilə daxil olun və API-ləri test edin.
Swagger URL:
http://localhost:8082/swagger-ui/index.html

**Mümkün Gələcək İnkişaflar**

Unit Test-lərin tam əhatə etməsi.
Mikroservice tam bolünmə.
Məhsul kataloqlarında çevik filter sistemləri.

**Əlaqə**
Hər hansı bir sualınız olduqda əlaqə saxlayın:

E-mail: nemet.memiyev1@gmail.com

GitHub : [https://github.com/NematMamiyev]()