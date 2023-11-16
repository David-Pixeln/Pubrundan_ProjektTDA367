import { ThemeContext } from "@constants/themes";
import { useContext } from "react";
import { View } from "react-native";
import { StyleSheet } from "react-native";
import MapView from "react-native-maps";


export default function Explore() {
  const theme = useContext(ThemeContext);

  const styles = StyleSheet.create({
    container: {
      flex: 1,
    },
    map: {
      width: '100%',
      height: '100%',
    }
  });
  

  return (
    <View style={styles.container}>
      <MapView style={styles.map} />
    </View>
  );
}