import 'package:cloud_firestore/cloud_firestore.dart' show DocumentSnapshot, Firestore, QuerySnapshot;
import 'package:flutter/material.dart';

class ReceiveData extends StatefulWidget {
  @override
  _ReceiveDataState createState() => _ReceiveDataState();
}

void initState() {}

class _ReceiveDataState extends State<ReceiveData> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text('Order List')),

      body: StreamBuilder<QuerySnapshot>(
          stream: Firestore.instance.collection('data').orderBy('trip_distance', descending: true).limit(5).snapshots(),
          builder:
              (BuildContext context, AsyncSnapshot<QuerySnapshot> snapshot) {
            if (snapshot.hasError)
              return new Text('Error: ${snapshot.error}');
            switch (snapshot.connectionState) {
              case ConnectionState.waiting:
                return new Text('Loading...');
              default:
                return new ListView(
                  children: snapshot.data.documents
                      .map((DocumentSnapshot document) {
                    return new ListTile(
                      title: new Text(document['trip_distance'].toString()),
                      subtitle: new Text(document['tpep_dropoff_datetime'].toString()),
                    );
                  }).toList(),
                  //)
                );
            }
          })
    );
  }
}








