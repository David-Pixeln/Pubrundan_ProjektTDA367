import { Text, View, ScrollView, Image } from "react-native";
import { SafeAreaView } from "react-native-safe-area-context";
import { HomePageStyles } from "@styles/home.styles";
import React, { useContext } from "react";
import { ThemeContext } from "@constants/themes";
import { Icon } from "@components/icon";
import * as VectorIcons from '@expo/vector-icons';


export default function Home() {
  const theme = useContext(ThemeContext);
  const styles = HomePageStyles(theme);
  
  return (
    <SafeAreaView style={styles.pageContainer}>
      <View style={styles.cardContainer}>
        <View style={styles.card}>
          <View style={styles.cardHeader}>
            <Text>
              User Name
            </Text>
            <Icon iconType={VectorIcons.Feather} name='clock'></Icon>
            <Text> 
              42:06:90
            </Text>
          </View>
         
          
        </View>
          
        <View style={styles.card}>
            
        </View>
      </View>
    </SafeAreaView>
  );
}