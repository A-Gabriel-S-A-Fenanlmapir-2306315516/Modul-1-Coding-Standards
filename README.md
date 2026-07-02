# E-Shop

Gabriel S.A. Fenanlampir  
2306315516  
ADPRO A

## Refleksi Modul 1

Setelah mengerjakan tutorial dan exercise pada Modul 1, saya sadar bahwa clean code adalah dasar yang penting untuk terus saya pegang. Pada awalnya alur pengerjaannya terasa sulit, tetapi setelah mencoba memahami langkah demi langkah, saya mulai melihat bagaimana setiap bagian kode saling terhubung. Bagian yang paling saya sukai adalah ketika konsep yang awalnya terasa abstrak mulai menjadi jelas saat saya menulis dan memperbaiki kode. Dari proses ini, saya belajar bahwa kesulitan dalam pemrograman bisa dihadapi dengan belajar, mencoba, dan tetap sabar saat menelusuri masalah. Saya merasa sudah memulai perjalanan ini dengan baik dan ingin terus memperbaiki kualitas kode saya pada modul-modul berikutnya.

## Refleksi Modul 2: CI/CD & DevOps

### Status Implementasi Saat Ini

Repository ini sudah memiliki beberapa bagian utama dari tutorial Modul 2, yaitu konfigurasi Gradle dengan plugin JaCoCo, workflow Continuous Integration di `.github/workflows/ci.yml`, workflow OSSF Scorecard di `.github/workflows/scorecard.yml`, serta `Dockerfile` untuk membantu proses packaging aplikasi. Saya juga sudah menjalankan `./gradlew.bat test jacocoTestReport` secara lokal dan build berhasil. Hasil JaCoCo saat ini menunjukkan instruction coverage sebesar 31% dan branch coverage sebesar 50%.

Namun, implementasi Modul 2 belum sepenuhnya lengkap. Saya belum menemukan workflow tambahan untuk code scanning/analysis seperti PMD atau SonarCloud selain OSSF Scorecard. Saya juga belum menemukan workflow auto-deploy ke PaaS maupun URL publik deployment aplikasi. Artinya, bagian Continuous Integration sudah ada, tetapi bagian Continuous Deployment masih perlu dilengkapi agar benar-benar sesuai dengan exercise Modul 2.

### Code Quality Issue yang Diperbaiki

Salah satu code quality issue yang sudah terlihat dari riwayat pengerjaan adalah penggunaan dependency yang terlalu bergantung pada implementasi konkret dan field injection. Strategi perbaikannya adalah mengarah ke dependency inversion dan constructor injection, seperti pada `ProductController` yang bergantung pada interface `ProductService` dan pada `ProductServiceImpl` yang menerima `ProductRepository` melalui constructor. Dengan pendekatan ini, dependency menjadi lebih eksplisit, lebih mudah diuji, dan kelas tidak terlalu terikat pada detail implementasi tertentu. Perbaikan seperti ini juga membantu menjaga kode tetap lebih mudah dirawat ketika jumlah fitur bertambah.

Pada pengerjaan Modul 3, beberapa issue lanjutan dari catatan ini sudah mulai diperbaiki, terutama pada fitur Car. Debug print yang sebelumnya ada di controller dihapus, dan dependency pada layer controller/service diarahkan ke interface serta constructor injection. Dengan begitu, struktur kode menjadi lebih konsisten dengan prinsip clean code dan SOLID yang mulai diterapkan.

### Evaluasi CI/CD

Menurut saya, implementasi saat ini sudah memenuhi sebagian definisi Continuous Integration. Workflow CI berjalan pada event `push` dan `pull_request`, menyiapkan Java 21, lalu menjalankan test suite menggunakan Gradle. Hal ini membuat perubahan kode dapat diverifikasi otomatis sehingga error lebih cepat terlihat sebelum perubahan digabungkan.

Untuk Continuous Deployment, implementasi saat ini belum sepenuhnya memenuhi definisinya. Repository memang sudah memiliki `Dockerfile`, sehingga aplikasi sudah lebih siap untuk dipaketkan dan dijalankan di environment deployment. Akan tetapi, belum ada workflow yang secara otomatis melakukan deployment ke PaaS dan belum ada URL publik aplikasi yang bisa dicatat. Agar benar-benar memenuhi Continuous Deployment, repository masih perlu menambahkan workflow deployment, menghubungkannya dengan layanan seperti Render atau Koyeb, lalu memastikan deployment berhasil setiap kali branch utama diperbarui.

## Refleksi Modul 3: Maintainability & OO Principles

### Prinsip yang Diterapkan

Pada Modul 3 ini saya menerapkan beberapa prinsip SOLID pada fitur Product dan Car. Prinsip SRP diterapkan dengan memisahkan tanggung jawab antara model, repository, service, controller, dan template. Contohnya, `CarController` hanya menangani request dan response web, `CarServiceImpl` mengatur operasi bisnis sederhana, sedangkan `CarRepository` menyimpan dan mengubah data Car. Prinsip DIP diterapkan dengan membuat controller bergantung pada interface `CarService`, dan service bergantung pada interface repository seperti `CarRepositoryInterface` dan `ProductRepositoryInterface`, bukan langsung pada detail implementasi konkret. Prinsip ISP diterapkan dengan menyediakan interface kecil yang hanya berisi method yang relevan untuk masing-masing resource. Prinsip OCP juga mulai diterapkan karena layer service dapat menerima implementasi repository lain, misalnya repository database, tanpa harus mengubah kode service selama kontraknya tetap sama.

### Keuntungan Menerapkan SOLID

Keuntungan utama dari penerapan SOLID adalah kode menjadi lebih mudah dipahami, diuji, dan dikembangkan. Dengan SRP, ketika ada perubahan pada tampilan Car, saya cukup mengubah template atau controller tanpa perlu menyentuh logika penyimpanan data di repository. Dengan DIP, `CarServiceImpl` tidak lagi bergantung langsung pada detail `CarRepository`; artinya implementasi penyimpanan data bisa diganti dari in-memory repository ke database repository dengan dampak perubahan yang lebih kecil. Interface kecil juga membuat kontrak antar kelas lebih jelas, sehingga unit test dapat dibuat lebih mudah karena dependency dapat diganti dengan implementasi lain. Contohnya, test `CarServiceImplTest` dapat membuat service dengan repository tertentu tanpa perlu menjalankan keseluruhan aplikasi Spring.

### Kerugian Jika SOLID Tidak Diterapkan

Jika SOLID tidak diterapkan, kode akan lebih mudah menjadi sulit dirawat saat fitur bertambah. Misalnya, jika controller langsung bergantung pada `CarServiceImpl`, maka setiap perubahan implementasi service dapat memaksa controller ikut berubah, padahal controller seharusnya hanya peduli pada kontrak layanan. Jika field injection dan dependency konkret dipakai terus-menerus, dependency kelas menjadi kurang eksplisit dan unit test lebih sulit dibuat. Jika satu kelas memegang terlalu banyak tanggung jawab, perubahan kecil pada satu aspek bisa menimbulkan bug pada aspek lain yang sebenarnya tidak berkaitan. Contohnya, jika controller juga mengatur penyimpanan data, maka perubahan cara menyimpan data Car dapat merusak alur HTTP endpoint `/car/listCar`.

## Refleksi Modul 4: Refactoring & TDD

### Kegunaan TDD

Menurut saya, alur TDD berguna karena memaksa saya memahami kebutuhan fitur sebelum menulis implementasi. Saat membuat fitur Order dan Payment, test membantu memperjelas happy path dan unhappy path, misalnya order tidak boleh dibuat tanpa product, status order hanya boleh memakai nilai yang valid, voucher hanya sukses jika formatnya benar, dan Bank Transfer harus memiliki `bankName` serta `referenceCode`. Dengan menulis test lebih dulu, saya bisa melihat perilaku yang diharapkan secara eksplisit dan lebih cepat tahu bagian mana yang rusak saat implementasi berubah. Untuk pengerjaan berikutnya, saya perlu lebih disiplin membuat commit kecil sesuai fase RED, GREEN, dan REFACTOR agar riwayat pengerjaan lebih mudah dinilai dan alur TDD lebih terlihat.

### Evaluasi F.I.R.S.T.

Unit test yang dibuat sudah cukup mengikuti prinsip F.I.R.S.T. Test bersifat fast karena berjalan sebagai unit test tanpa membutuhkan database, server eksternal, atau browser. Test juga independent karena setiap test membuat data sendiri melalui `setUp` atau helper, sehingga hasil satu test tidak bergantung pada urutan test lain. Test bersifat repeatable karena repository yang dipakai masih in-memory dan dibuat ulang pada setiap test. Assertion pada test juga self-validating karena hasil benar atau salah langsung terlihat dari assertion JUnit, bukan dari pemeriksaan manual. Dari sisi timely, test dibuat untuk mendampingi implementasi fitur baru Order dan Payment; ke depannya saya perlu menjaga agar test benar-benar ditulis sebelum kode produksi ketika mengikuti TDD secara penuh.
