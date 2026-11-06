import 'package:flutter/material.dart';
import 'package:submit_me/auth_page.dart';
import 'package:submit_me/login_page.dart';
import 'package:submit_me/registration_page.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
  debugShowCheckedModeBanner: false,
  theme: ThemeData(
    useMaterial3: true,
    scaffoldBackgroundColor: const Color.fromARGB(255, 200, 217, 236),
    colorScheme: ColorScheme.fromSeed(
      seedColor: const Color(0xFF565B61),
      brightness: Brightness.light,
      surface: const Color(0xFFF2F3F4),
    ),
    appBarTheme: const AppBarTheme(
      backgroundColor: Color.fromARGB(255, 70, 136, 216),
      foregroundColor: Colors.white,
    ),
  ),
  home: const HomePage(),
  routes: {
    '/login': (context) => const LoginPage(),
    '/register': (context) => const RegistrationPage(),
  },
);
  }
}