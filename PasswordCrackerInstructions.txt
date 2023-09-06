# PasswordCracker

Takes in a "test_file.txt" with a series of hashes including sha256, sha1, and md5. It then decrypts then by referecing passwords from "Top-10000-Passwords.txt"and comparing the hashes. For the fourth hash, the program brute forces it using 0-z and compares the hashes with the test file. This program uses parallel streaming to brute force the passwords faster. 
