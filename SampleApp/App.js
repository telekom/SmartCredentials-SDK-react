import { StatusBar } from 'expo-status-bar';
import React from 'react';
import { NativeModules, Button, StyleSheet, Text, View } from 'react-native';

const { AuthenticationModule } = NativeModules;

const NewModuleButton = () => {
  const onPress = () => {
    console.log('Invoke Native Methods: ');
    AuthenticationModule.login();
  };

  return (
    <Button
      title="LOG IN HERE"
      color="#000000"
      onPress={onPress}
    />
  );
};

export default NewModuleButton;
