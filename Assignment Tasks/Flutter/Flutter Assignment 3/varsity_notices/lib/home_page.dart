import 'package:flutter/material.dart';

class HomePage extends StatelessWidget {
const HomePage({super.key});

@override
Widget build(BuildContext context) {

final List<Map<String, String>> notices = [
{
'image': 'assets/notices/notice_001.png',
'dateTime': '15 Oct 2026 • 09:30 PM',
},
{
'image': 'assets/notices/notice_002.png',
'dateTime': '15 Oct 2026 • 10:15 PM',
},
{
'image': 'assets/notices/notice_003.png',
'dateTime': '15 Oct 2026 • 10:45 PM',
},
{
'image': 'assets/notices/notice_004.png',
'dateTime': '14 Oct 2026 • 08:30 PM',
},
{
'image': 'assets/notices/notice_005.png',
'dateTime': '14 Oct 2026 • 05:15 PM',
},
{
'image': 'assets/notices/notice_006.png',
'dateTime': '13 Oct 2026 • 11:00 AM',
},
{
'image': 'assets/notices/notice_007.png',
'dateTime': '12 Oct 2026 • 02:30 PM',
},
{
'image': 'assets/notices/notice_008.png',
'dateTime': '11 Oct 2026 • 09:00 AM',
},
{
'image': 'assets/notices/notice_009.png',
'dateTime': '10 Oct 2026 • 04:45 PM',
},
{
'image': 'assets/notices/notice_010.png',
'dateTime': '09 Oct 2026 • 12:15 PM',
},
{
'image': 'assets/notices/notice_011.png',
'dateTime': '08 Oct 2026 • 10:00 AM',
},
];

```
return Scaffold(
  appBar: AppBar(
    title: const Text("LU Notices"),
    backgroundColor: const Color.fromARGB(255, 222, 187, 10),
    actions: [
      IconButton(
        onPressed: () {},
        icon: const Icon(Icons.search),
      ),
      IconButton(
        onPressed: () {},
        icon: const Icon(Icons.person),
      ),
    ],
  ),

  drawer: Drawer(
    child: Column(
      children: [
        UserAccountsDrawerHeader(
          accountName: const Text("Abhijit Das Ayon"),
          accountEmail: const Text("abhijitdasayon@gmail.com"),
          decoration: const BoxDecoration(
            color: Color.fromARGB(255, 23, 2, 255),
          ),
        ),

        ListTile(
          hoverColor: const Color.fromARGB(255, 245, 67, 7),
          splashColor: const Color.fromARGB(255, 180, 237, 8),
          trailing: const Icon(Icons.home),
          title: const Text("Homepage"),
          onTap: () {},
        ),

        const Divider(
          color: Color.fromARGB(33, 204, 244, 4),
        ),

        ListTile(
          trailing: const Icon(Icons.settings),
          title: const Text("Settings"),
          hoverColor: const Color.fromARGB(255, 255, 0, 200),
          splashColor: Colors.greenAccent,
          onTap: () {},
        ),

        ListTile(
          trailing: const Icon(Icons.logout),
          title: const Text("Logout"),
          hoverColor: const Color.fromARGB(255, 254, 153, 0),
          splashColor: const Color.fromARGB(255, 109, 3, 248),
          onTap: () {},
        ),
      ],
    ),
  ),

  body: Column(
    children: [

      SingleChildScrollView(
        scrollDirection: Axis.horizontal,
        child: Row(
          children: [

            Padding(
              padding: const EdgeInsets.all(8.0),
              child: TextButton(
                onPressed: () {},
                style: TextButton.styleFrom(
                  backgroundColor: const Color.fromARGB(255, 254, 8, 197),
                  foregroundColor: const Color.fromARGB(255, 253, 253, 253),
                  side: const BorderSide(color: Colors.deepPurple),
                  elevation: 3,
                  shadowColor: const Color.fromARGB(255, 0, 0, 0),
                ),
                child: const Text("All Notices"),
              ),
            ),

            Padding(
              padding: const EdgeInsets.all(8.0),
              child: OutlinedButton(
                onPressed: () {},
                style: TextButton.styleFrom(
                  backgroundColor: const Color.fromARGB(255, 35, 4, 206),
                  foregroundColor: const Color.fromARGB(255, 253, 253, 253),
                  side: const BorderSide(color: Colors.deepPurple),
                  elevation: 3,
                  shadowColor: const Color.fromARGB(255, 0, 0, 0),
                ),
                child: const Text("Exams"),
              ),
            ),


            ElevatedButton(
              onPressed: () {},
              child: const Text("Latest"),
              style: ElevatedButton.styleFrom(
                backgroundColor: const Color.fromARGB(255, 242, 0, 0),
                foregroundColor: const Color.fromARGB(255, 253, 253, 253),
              ),
            ),

              IconButton(
              onPressed: () {},
              icon: const Icon(Icons.login),
            ),

          ],
        ),
      ),



      Expanded(
        child: ListView.builder(
          itemCount: notices.length,

          itemBuilder: (context, index) {
            return Padding(
              padding: const EdgeInsets.symmetric(
                horizontal: 12.0,
                vertical: 8.0,
              ),

              child: Column(
                crossAxisAlignment: CrossAxisAlignment.stretch,
                children: [



                  Card(
                    elevation: 25,
                    shadowColor: const Color.fromARGB(99, 0, 0, 0),
                    shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(12),
                    ),
                    child: ClipRRect(
                      borderRadius: BorderRadius.circular(12),

                      child: Image.asset(
                        notices[index]['image']!,
                        width: double.infinity,
                        fit: BoxFit.fitWidth,
                      ),
                    ),
                  ),

                  Padding(
                    padding: const EdgeInsets.only(
                      top: 4.0,
                      left: 4.0,
                      right: 4.0,
                    ),

                    child: Text(
                      notices[index]['dateTime']!,
                      textAlign: TextAlign.center,
                      style: TextStyle(
                        fontSize: 10,
                        color: const Color.fromARGB(255, 0, 0, 0),
                      ),
                    ),
                  ),

                ],
              ),
            );
          },
        ),
      ),

    ],
  ),

  floatingActionButton: FloatingActionButton(
    onPressed: () {},
    backgroundColor: const Color.fromARGB(255, 255, 12, 20),
    child: const Icon(Icons.add),
  ),
);
}
}
