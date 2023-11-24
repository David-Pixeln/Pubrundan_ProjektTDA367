import { Icon } from "@components/icon";
import { style } from "./PostCard.style";
import { View, Image } from "react-native";
import { useContext } from "react";
import { ThemeContext } from "@constants/themes";
import { Text } from "@components";
import * as VectorIcons from '@expo/vector-icons';


export type PostCardProps = {
  userName: string,
  postTimestamp: DateTime,
  profileImagePath: string,
  postImagePath: string,
  postText: string,
}

export function PostCard(props: PostCardProps) {
  const theme = useContext(ThemeContext)
  const styles = style(theme);

  return (
    <View style={styles.card}>
      <View style={styles.cardHeader}>
        <View style={styles.headerItemContainer}>
          <View style={styles.profilePictureContainer}>
            <Image style={styles.profilePicture} source={require({props.profileImagePath})} />
            {/* '@assets/images/Portrait_Placeholder.png' */}
          </View>
          <Text>{props.userName}</Text>
        </View>
        <View style={styles.headerItemContainer}>
          <Icon iconType={VectorIcons.Feather} name='clock' />
          <Text>42:06:90</Text>
        </View>
      </View>
      
      <View style={styles.postImageContainer}>
        <Image style={styles.postImage} source={require({props.postImagePath})} />
        {/* '@assets/images/cocktail.jpg' */}
      </View>

      <View>
        <Text>{props.postText}</Text>
      </View>
    </View>
  )
}