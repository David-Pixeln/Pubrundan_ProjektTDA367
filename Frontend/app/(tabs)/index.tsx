import { ImageSourcePropType, ScrollView } from "react-native";
import { SafeAreaView } from "react-native-safe-area-context";
import { HomePageStyles } from "@styles/home.styles";
import React, { useContext } from "react";
import { ThemeContext } from "@constants/themes";
import { PostCard } from "@components/postCard";


type Post = {
  id: number,
  author: string,
  authorProfileImage: ImageSourcePropType,
  content: string,
  image: ImageSourcePropType,
  date: string,
}


export default function Home() {
  const theme = useContext(ThemeContext);
  const styles = HomePageStyles(theme);
  
  const posts: Array<Post> = [
    {
      id: 69,
      author: "chrille0313",
      authorProfileImage: require("@assets/images/Portrait_Placeholder.png"),
      content: "Abudhabi",
      image: require("@assets/images/cocktail.jpg"),
      date: "2023/11/24"
    },
    {
      id: 2,
      author: "Din mamma",
      authorProfileImage: require("@assets/images/Portrait_Placeholder.png"),
      content: "lalalalal ballong",
      image: require("@assets/images/cocktail.jpg"),
      date: "1997/04/31",
    },
    {
      id: 3,
      author: "Granaten",
      authorProfileImage: require("@assets/images/Portrait_Placeholder.png"),
      content: "Have a good day *Watermelon*",
      image: require("@assets/images/cocktail.jpg"),
      date: "15:58 - 24/11/2023",
    },
  ];

  return (
    <SafeAreaView style={styles.pageContainer}>
      <ScrollView contentContainerStyle={{rowGap: 16}} style={styles.cardContainer}>
        { 
          posts.map((post) => (
            <PostCard key={post.id} 
              author={post.author} 
              profileImage={post.authorProfileImage}
              timestamp={post.date} 
              postImage={post.image}
              description={post.content}
            />
          ))
        }
      </ScrollView>
    </SafeAreaView>
  );
}