import { View, ScrollView, Image } from "react-native";
import { SafeAreaView } from "react-native-safe-area-context";
import { HomePageStyles } from "@styles/home.styles";
import React, { useContext } from "react";
import { ThemeContext } from "@constants/themes";
import { PostCard } from "@components/postCard";


export default function Home() {
  const theme = useContext(ThemeContext);
  const styles = HomePageStyles(theme);
  
  const posts = [];

  {
    for (let index = 0; index < 10; index++)
      posts.push(<PostCard />)
  }

  return (
    <SafeAreaView style={styles.pageContainer}>
      <ScrollView contentContainerStyle={{rowGap: 16}} style={styles.cardContainer}>
        { posts }
      </ScrollView>
    </SafeAreaView>
  );
}