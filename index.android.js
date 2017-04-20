/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, { Component } from 'react';
import {
  AppRegistry,
  Button,
  NativeModules,
  StyleSheet,
  Text,
  View
} from 'react-native';


const LatencyTest = NativeModules.LatencyTest;

var start;
var start2;

export default class BridgeLatencyTest extends Component {

  constructor(props, context) {
    super(props, context);
  }

  render() {
    return (
      <View style={styles.container}>
        <Text style={styles.welcome}>
          Welcome to React Native!
        </Text>
        <Button style={styles.instructions}
          onPress={() => {
            start = nativePerformanceNow();
            LatencyTest.testLatency().then((result) => {
              var callbackTime = nativePerformanceNow();
              console.log('Binary total time:', callbackTime - start);
            });
          }}
          title="Test Binary"
        />

        <Button style={styles.instructions}
          onPress={
            () => {
              start = nativePerformanceNow();
              LatencyTest.testLatencyJSONString().then((result) => {
                var data = JSON.parse(result);
                var callbackTime = nativePerformanceNow();
                console.log('JSON total time:', callbackTime - start);
              });
            }
          }
          title="Test JSON"
        />

        <Button style={styles.instructions}
          onPress={
            () => {
              start = nativePerformanceNow();
              LatencyTest.testLatencyJacksonString().then((result) => {
                var data = JSON.parse(result);
                var callbackTime = nativePerformanceNow();
                console.log('Jackson total time:', callbackTime - start);
              });
            }
          }
          title="Test Jackson"
        />
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'space-around',
    backgroundColor: '#F5FCFF',
  },
  welcome: {
    fontSize: 10,
    textAlign: 'center',
    margin: 5,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
  },
});

AppRegistry.registerComponent('BridgeLatencyTest', () => BridgeLatencyTest);
