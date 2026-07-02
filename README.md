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

Masih ada beberapa issue yang sebaiknya diperbaiki berikutnya, misalnya `System.out.println` di controller dan field injection pada beberapa kelas Car. Issue seperti itu sebaiknya diganti dengan logger atau dihapus jika hanya dipakai untuk debugging, lalu dependency service/repository sebaiknya dibuat final dan disuntikkan melalui constructor. Dengan begitu, struktur kode akan lebih konsisten dengan prinsip clean code dan SOLID yang sudah mulai diterapkan.

### Evaluasi CI/CD

Menurut saya, implementasi saat ini sudah memenuhi sebagian definisi Continuous Integration. Workflow CI berjalan pada event `push` dan `pull_request`, menyiapkan Java 21, lalu menjalankan test suite menggunakan Gradle. Hal ini membuat perubahan kode dapat diverifikasi otomatis sehingga error lebih cepat terlihat sebelum perubahan digabungkan.

Untuk Continuous Deployment, implementasi saat ini belum sepenuhnya memenuhi definisinya. Repository memang sudah memiliki `Dockerfile`, sehingga aplikasi sudah lebih siap untuk dipaketkan dan dijalankan di environment deployment. Akan tetapi, belum ada workflow yang secara otomatis melakukan deployment ke PaaS dan belum ada URL publik aplikasi yang bisa dicatat. Agar benar-benar memenuhi Continuous Deployment, repository masih perlu menambahkan workflow deployment, menghubungkannya dengan layanan seperti Render atau Koyeb, lalu memastikan deployment berhasil setiap kali branch utama diperbarui.
