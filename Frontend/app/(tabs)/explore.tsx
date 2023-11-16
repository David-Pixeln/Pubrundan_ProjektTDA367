import { ThemeContext } from "@constants/themes";
import { useContext } from "react";
import { Text } from "react-native";
import { SafeAreaView } from "react-native-safe-area-context";
import { StyleSheet } from "react-native";

export default function Explore() {
  const theme = useContext(ThemeContext);

  const styles = StyleSheet.create({
    container: {
      flex: 1,
    },
  });
  

  return (
    <SafeAreaView style={styles.container}>
      <Text style={{color: theme.colors.text}}>Explore</Text>
    </SafeAreaView>
  );
}