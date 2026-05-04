
# Üretim Yönetim ve Takip Sistemi

Bu proje, üretim süreçlerinde makine, sipariş, planlama, duruş/kayıp ve raporlama işlemlerini takip etmek amacıyla geliştirilmiş bir masaüstü uygulamasıdır.

Proje Java programlama dili ile geliştirilmiştir. Kullanıcı arayüzü için Java Swing, veritabanı işlemleri için SQLite ve JDBC kullanılmıştır.

## Kullanılan Teknolojiler

- Java
- Java Swing
- SQLite
- JDBC
- Eclipse IDE

## Projenin Temel Özellikleri

- Kullanıcı kayıt ve giriş işlemleri yapılabilir.
- Admin ve operatör rolleri ayrılabilir.
- Makine bilgileri eklenebilir, güncellenebilir ve silinebilir.
- Sipariş bilgileri eklenebilir, güncellenebilir ve silinebilir.
- Siparişlere iş emri eklenebilir.
- Planlama ekranında iş emirleri takvim üzerinde görüntülenebilir.
- Admin, iş emirlerinin plan tarihini ve durumunu güncelleyebilir.
- Admin, planlama ekranından iş emri silebilir.
- Operatör, planlama ekranını yalnızca görüntüleme amacıyla kullanabilir.
- Duruş/kayıp kayıtları oluşturulabilir.
- Duruş/kayıp ekranında başlangıç ve bitiş saatine göre süre otomatik hesaplanır.
- Raporlama ekranında sistem özeti, duruş analizi ve duruş/kayıp kayıtları görüntülenebilir.

## Proje Yapısı

UretimYonetimTakipSistemi
├── src
│   ├── database
│   ├── interfaces
│   ├── model
│   ├── service
│   └── ui
├── lib
│   └── sqlite-jdbc-3.53.0.0.jar
├── .classpath
├── .project
└── README.md


## Paket Açıklamaları

### database

Veritabanı bağlantısı ve oturum bilgileri bu pakette yer alır.

Bu paketteki temel sınıflar:

* `Veritabani.java`
* `Session.java`

`Veritabani.java`, SQLite bağlantısını kurar ve gerekli tabloları oluşturur.
`Session.java`, giriş yapan aktif kullanıcının kullanıcı adı ve rol bilgisini tutar.

### model

Projede kullanılan temel veri sınıfları bu pakette yer alır.

Örnek model sınıfları:

* `Kullanici.java`
* `Admin.java`
* `Operator.java`
* `Makine.java`
* `Siparis.java`
* `Planlama.java`
* `DurusKayip.java`

### service

Veritabanı işlemlerinin yapıldığı sınıflar bu pakette yer alır.

Örnek service sınıfları:

* `KullaniciService.java`
* `MakineService.java`
* `SiparisService.java`
* `PlanlamaService.java`
* `DurusKayipService.java`
* `RaporService.java`

Bu sınıflarda JDBC kullanılarak ekleme, silme, güncelleme ve listeleme işlemleri yapılır.

### interfaces

Service sınıflarının uyguladığı interface yapıları bu pakette yer alır.

Örnek interface sınıfları:

* `IKullaniciIslemleri.java`
* `IMakineIslemleri.java`
* `ISiparisIslemleri.java`
* `IPlanlamaIslemleri.java`
* `IDurusKayipIslemleri.java`
* `IRaporIslemleri.java`

### ui

Kullanıcı arayüzü ekranları bu pakette yer alır.

Örnek arayüz sınıfları:

* `GirisEkrani.java`
* `KayitEkrani.java`
* `AdminMenuEkrani.java`
* `OperatorMenuEkrani.java`
* `MakineGirisEkrani.java`
* `SiparisEkrani.java`
* `PlanlamaEkrani.java`
* `DurusKayipEkrani.java`
* `RaporEkrani.java`
* `OrtakEkran.java`

`OrtakEkran.java`, işlem ekranlarında kullanılan ortak pencere yapısını ve üst menüyü içerir.

## Kullanıcı Rolleri

Projede iki kullanıcı rolü bulunmaktadır: Admin ve Operatör.

### Admin

Admin kullanıcısı sistemde daha geniş yetkilere sahiptir.

Admin kullanıcısı:

* Makine girişi yapabilir.
* Sipariş girişi yapabilir.
* Sipariş silebilir.
* Planlama ekranında güncelleme yapabilir.
* Planlama ekranında iş emri ekleyebilir/silebilir.
* Raporlama ekranını görüntüleyebilir.

### Operatör

Operatör kullanıcısı daha sınırlı yetkilere sahiptir.

Operatör kullanıcısı:

* Makine ekranını kullanabilir.
* Duruş/kayıp kaydı oluşturabilir.
* Planlama ekranını yalnızca görüntüleyebilir.
* Raporlama ekranını görüntüleyebilir.
* Sipariş girişi yapamaz.
* Planlama ekranında güncelleme veya silme işlemi yapamaz.

## OOP Kullanımı

Projede nesne yönelimli programlama yapıları kullanılmıştır.

### Encapsulation

Model sınıflarında değişkenler `private` olarak tanımlanmıştır. Bu değişkenlere erişim getter metotları ile sağlanmıştır.

### Inheritance

Projede kalıtım yapısı kullanılmıştır.

Kullanıcı rolleri için:


public class Admin extends Kullanici
public class Operator extends Kullanici


Ekran yapıları için:

public class OrtakEkran extends JFrame
public class SiparisEkrani extends OrtakEkran
public class PlanlamaEkrani extends OrtakEkran
public class RaporEkrani extends OrtakEkran
public class MakineGirisEkrani extends OrtakEkran
public class DurusKayipEkrani extends OrtakEkran


`OrtakEkran` sınıfı sayesinde işlem ekranlarında kullanılan ortak pencere ayarları, üst menü, renkler ve rol kontrolleri tek merkezde toplanmıştır.

### Polymorphism

`Kullanici` sınıfında bulunan `yetkiBilgisi()` metodu, `Admin` ve `Operator` sınıflarında override edilmiştir. Böylece kullanıcının rolüne göre farklı yetki bilgisi döndürülebilmektedir.

### Interface Kullanımı

Service sınıfları ilgili interface yapılarını implement etmektedir.

Örnek:


public class SiparisService implements ISiparisIslemleri
public class MakineService implements IMakineIslemleri


Bu yapı ile servis sınıflarında yapılacak işlemler soyut olarak tanımlanmış ve ilgili sınıflarda uygulanmıştır.

## Veritabanı

Projede SQLite veritabanı kullanılmıştır.

Veritabanı dosyası GitHub’a eklenmemiştir. Program ilk çalıştırıldığında `uys.db` dosyası otomatik olarak oluşturulur ve gerekli tablolar hazırlanır.

Oluşturulan temel tablolar:

* `users`
* `machines`
* `siparisler`
* `is_emirleri`
* `downtimes`

## JDBC Kullanımı

Java uygulaması ile SQLite veritabanı arasındaki bağlantı JDBC ile sağlanmıştır.

Projede JDBC kullanılarak:

* Kullanıcı kayıt ve giriş işlemleri
* Makine ekleme, güncelleme, silme ve listeleme işlemleri
* Sipariş ekleme, güncelleme, silme ve listeleme işlemleri
* Planlama işlemleri
* Duruş/kayıp işlemleri
* Raporlama işlemleri

gerçekleştirilmiştir.

SQLite bağlantısı için kullanılan JDBC jar dosyası proje içinde `lib` klasöründe yer almaktadır.


lib/sqlite-jdbc-3.53.0.0.jar


Bu nedenle proje farklı bir bilgisayarda açıldığında ayrıca jar dosyası indirmeye gerek yoktur.

## İlk Çalıştırma

Bu projede hazır veritabanı dosyası bulunmadığı için ilk kullanımda kullanıcı oluşturulmalıdır.

İlk çalıştırma adımları:

1. Proje Eclipse ile açılır.
2. `GirisEkrani.java` çalıştırılır.
3. Açılan giriş ekranında **Kayıt Ol** butonuna basılır.
4. Admin veya Operatör rolünde kullanıcı oluşturulur.
5. Oluşturulan kullanıcı adı, şifre ve rol bilgisi ile giriş yapılır.

## Eclipse Üzerinde Çalıştırma

Projeyi Eclipse üzerinde çalıştırmak için:

1. Repository bilgisayara indirilir.
2. Eclipse açılır.
3. `File > Import > Existing Projects into Workspace` seçilir.
4. Proje klasörü seçilir.
5. Proje import edilir.
6. `GirisEkrani.java` çalıştırılır.

## Temel Ekranlar

### Giriş Ekranı

Kullanıcı adı, şifre ve rol bilgisi ile sisteme giriş yapılır.

### Kayıt Ekranı

Yeni admin veya operatör kullanıcısı oluşturulur.

### Admin Menü

Admin kullanıcısının erişebileceği ekranlara yönlendirme yapılır.

### Operatör Menü

Operatör kullanıcısının erişebileceği ekranlara yönlendirme yapılır.

### Makine Girişi

Makine tipi, makine kodu, bölüm, kapasite, bakım periyodu ve lokasyon bilgileri yönetilir.

### Sipariş Girişi

Sipariş adı, sipariş kodu, müşteri, ürün adı, miktar ve termin tarihi bilgileri yönetilir. Siparişlere iş emirleri eklenebilir.

### Planlama

İş emirleri takvim üzerinde görüntülenir. Admin, planlama tarihi ve durum güncellemesi yapabilir. Operatör yalnızca görüntüleme yapabilir.

### Duruş/Kayıp

Makineye ait duruş veya kayıp bilgileri kaydedilir. Başlangıç ve bitiş zamanı seçildiğinde süre otomatik hesaplanır.

### Raporlama

Sistem özeti, duruş analizi ve duruş/kayıp kayıtları görüntülenir.

## Raporlama Özellikleri

Raporlama ekranında şu bilgiler görüntülenir:

* Toplam makine sayısı
* Toplam sipariş sayısı
* Toplam duruş/kayıp sayısı
* Toplam iş emri sayısı
* Planlı duruş sayısı
* Plansız duruş sayısı
* En çok görülen duruş nedeni
* Duruş/kayıp rapor tablosu

## Notlar

* Program ilk çalıştırıldığında `uys.db` dosyası otomatik oluşur.
* İlk giriş için önce kayıt ekranından kullanıcı oluşturulmalıdır.
* Admin ve operatör rolleri farklı yetkilere sahiptir.
* Veritabanı işlemleri JDBC ile yapılmaktadır.
* Kullanıcı arayüzü Java Swing ile geliştirilmiştir.
* Proje Eclipse IDE üzerinde hazırlanmıştır.


