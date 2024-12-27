# Toko Elektronik Management System

## Deskripsi Proyek
Proyek ini adalah sebuah sistem manajemen toko elektronik berbasis GUI (Graphical User Interface) yang dikembangkan menggunakan *Java Swing*. Aplikasi ini memungkinkan admin untuk mengelola produk elektronik seperti menambah, menghapus, dan memperbarui produk, serta memberikan fitur bagi pelanggan untuk melihat daftar produk berdasarkan kategori.

### Fitur Utama
1. *Login Sistem*
   - *Admin*: Mengelola data produk.
   - *Customer*: Melihat produk berdasarkan kategori.

2. *Manajemen Produk oleh Admin*
   - Menambah produk baru.
   - Memperbarui produk yang sudah ada.
   - Menghapus produk dari daftar.
   - Menyimpan dan memuat data produk menggunakan file.

3. *Kategori Produk*
   - Produk dikelompokkan berdasarkan kategori seperti:
     - TV
     - Laptop
     - Ponsel
     - Mesin Cuci
     - AC
     - Kulkas

4. *Pengelompokan Produk untuk Customer*
   - Customer dapat memfilter produk berdasarkan kategori tertentu.

---

## Struktur Proyek
Berikut adalah file dan class utama yang digunakan dalam proyek ini:

### File Java
1. *MainApp.java*
   - Entry point untuk aplikasi. Menampilkan frame login.

2. *LoginFrame.java*
   - Frame untuk login admin atau customer.

3. *AdminFrame.java*
   - Frame untuk admin mengelola produk.

4. *CustomerFrame.java*
   - Frame untuk customer melihat daftar produk berdasarkan kategori.

5. *User.java*
   - Class untuk merepresentasikan data pengguna (admin atau customer).

6. *Product.java*
   - Class untuk merepresentasikan data produk.

7. *FileHandler.java*
   - Class untuk menyimpan dan memuat data produk ke/dari file.

### File Eksternal
- *products.dat*
  - File yang digunakan untuk menyimpan data produk secara persisten.

---

## Cara Menjalankan Aplikasi
1. Pastikan Anda memiliki *JDK 8+* terinstal di sistem Anda.
2. Clone atau unduh repository ini.
3. Buka project di IDE seperti IntelliJ IDEA atau Eclipse.
4. Jalankan file MainApp.java sebagai aplikasi Java.

---

## Fungsionalitas Admin
1. *Login sebagai Admin*:
   - Username: admin
   - Password: admin123

2. *Kelola Produk*:
   - Masukkan informasi produk (ID, Nama, Harga, Stok, Kategori, Gambar).
   - Klik tombol *Tambah* untuk menambah produk.
   - Klik tombol *Update* untuk memperbarui produk yang dipilih.
   - Klik tombol *Hapus* untuk menghapus produk yang dipilih.
   - Klik tombol *Clear* untuk mengosongkan input.

3. *Logout*:
   - Klik menu *Akun* dan pilih *Logout*.

---

## Fungsionalitas Customer
1. *Login sebagai Customer*:
   - Username: customer
   - Password: customer123

2. *Filter Produk*:
   - Pilih kategori dari dropdown untuk melihat produk dalam kategori tersebut.

---

## Teknologi yang Digunakan
- *Java 8+*
- *Java Swing* untuk GUI
- *Object Serialization* untuk penyimpanan data produk

---

## Pengembangan Lanjutan
1. *Keamanan*:
   - Implementasi enkripsi password.
2. *Database*:
   - Migrasi penyimpanan data ke database seperti MySQL atau SQLite.
3. *User Interface*:
   - Penyempurnaan desain antarmuka agar lebih modern dan responsif.
4. *Fitur Tambahan*:
   - Penambahan fitur pencarian produk berdasarkan nama.
   - Pembuatan sistem transaksi pembelian untuk customer.

---

## Kontak Pengembang
Jika Anda memiliki pertanyaan atau saran, silakan hubungi:
- *IrfanMaulana9* di dalam github
- *Github*: [TokoElektronik](https://github.com/IrfanMaulana9/UAP-ElectronicShop.git)

---

Terima kasih telah menggunakan aplikasi Toko Elektronik Management System
