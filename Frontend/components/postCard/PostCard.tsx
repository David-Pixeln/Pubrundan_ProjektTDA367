import { Icon } from "@components/icon";
import { style } from "./PostCard.style";
import { View, Image, ImageSourcePropType } from "react-native";
import { useContext } from "react";
import { ThemeContext } from "@constants/themes";
import { Text } from "@components";
import * as VectorIcons from '@expo/vector-icons';


export type PostCardProps = {
  author: string,
  timestamp: string,
  profileImage: ImageSourcePropType,
  postImage: ImageSourcePropType,
  description: string,
}

export function PostCard(props: PostCardProps) {
  const theme = useContext(ThemeContext)
  const styles = style(theme);

  return (
    <View style={styles.card}>
      <View style={styles.cardHeader}>
        <View style={styles.headerItemContainer}>
          <View style={styles.profilePictureContainer}>
            <Image style={styles.profilePicture} source={props.profileImage} />
          </View>
          <Text style={styles.authorText}>{props.author}</Text>
        </View>
        <View style={styles.headerItemContainer}>
          <Icon iconType={VectorIcons.Feather} name='clock' />
          <Text>{props.timestamp}</Text>
        </View>
      </View>
      
      <View style={styles.postImageContainer}>
        <Image style={styles.postImage} source={props.postImage} />
      </View>

      <View>
        <Text><Text style={styles.authorText}>{props.author}</Text> {props.description}</Text>
      </View>
    </View>
  )
}