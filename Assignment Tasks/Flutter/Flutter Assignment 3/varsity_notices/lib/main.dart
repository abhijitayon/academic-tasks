import 'package:flutter/material.dart';
import 'package:varsity_notices/home_page.dart';

void main() {
  runApp(const varsity_notices());
}

class varsity_notices extends StatelessWidget {
  const varsity_notices({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      //theme: ThemeData.dark(),
      home: const HomePage(),
    );
  }
}